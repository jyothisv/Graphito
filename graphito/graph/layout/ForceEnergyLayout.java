package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.graph.layout.Vector2D;
import org.jgrapht.Graph;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
// import graphito.graph.layout.Stopwatch;


public class ForceLayoutMod {
    private static double HEIGHT = 600;
    private static double WIDTH = 800;
    private static double dampen = 0.9;

    private static double attraction(double k, Vector2D x)
    {
        return x.mod2()/k;
    }

    private static double repulsion(double k, Vector2D x)
    {
        return -k*k/(x.mod());
    }


    private static Vertex[] adjacent(Graph<Vertex, Edge> graph, Vertex v)
    {
        Vertex[] neigh = new Vertex[graph.edgesOf(v).size()];

        int i = 0;
        for (Edge e: graph.edgesOf(v)) {
            Vertex src = e.getSource();
            if (src != v)
                neigh[i++] = src;
            else neigh[i++] = e.getDest();
        }

        return neigh;
    }



    static int progress = 0;
    

    private static double cool(double step, double tot, double oldTot)
    {
        if (tot < oldTot) {
            ++progress;
            if (progress > 5) {
                progress = 0;
                step /= dampen;
            }
        } else { progress = 0;
            step *= dampen;
        }
        // step *= dampen;
        return step;
    }
    



    // Implementation of Fruchterman & Reingold's Graph Layout algorithm.
    
    public static void layout(Graph<Vertex, Edge> graph)
    {
        Set<Vertex> verts = graph.vertexSet();
        Set<Edge> edges = graph.edgeSet();

        //      Stopwatch sw = new Stopwatch();
        
        int N = verts.size();

        Random randGen = new Random();

        HashMap<Vertex, Vector2D> pos = new HashMap<Vertex, Vector2D>();


        // Assume that no points are assigned the exactly same positions
        for (Vertex v : verts) {
            pos.put(v, new Vector2D(WIDTH*randGen.nextDouble()/2.0, HEIGHT*randGen.nextDouble()/2.0));
             
             // System.out.println("Intitial: " + v.getId() + " " + pos.get(v));
        }

        // if N <= 1; Nothing more to do
        if (N <= 1)
            return;
        
        // System.out.println("Finished Initialization at: " + sw.elapsedTime());
        
        double k = Math.sqrt(WIDTH*HEIGHT/N);
        double t = 1; //Math.min(WIDTH, HEIGHT)/3.0;
        double step =  Math.min(WIDTH, HEIGHT)/3.0;
        double totalEnergy = 1.0/0.0; // Infinity
        double oldTotalEnergy = 0.0;

        Vector2D force = new Vector2D();
        Vector2D delta = new Vector2D();
        
        while (true) {
            // System.out.printf("%d th round\n", i);



            // System.out.println(sw.elapsedTime());
            oldTotalEnergy = totalEnergy;

            
            totalEnergy = 0.0;
            
            for (Vertex v: verts) {

                // force = (0, 0)
                force.zero();


                for (Edge e: graph.edgesOf(v)) {
                    Vertex u = e.getSource();

                    if (u == v)
                        u = e.getDest();
                    

                    // delta = u - v
                    pos.get(u).minus(pos.get(v), delta);

                    // double magn = delta.mod();

                    double attract = attraction(k, delta);
                    force.add(delta.norm().mult(attract));
                }

                
                for (Vertex u: verts) {
                    if (v != u) {
                        pos.get(u).minus(pos.get(v), delta);
                        
                        double repulse = repulsion(k, delta);
                        force.add(delta.norm().mult(repulse));
                    }
                }
                

                Vector2D vpos = pos.get(v);

                
                vpos.add(force.norm().mult(step)); // .norm().mult(Math.min(t, force.mod())));     // Math.min(disp.mod(), t)));

                //Vector2D delta = vpos2.minus(vpos);

                // System.out.println("Final delta = " + delta);
                
                // double x = vpos.getX(); // Math.min(WIDTH/2, Math.max(-WIDTH/2, vpos.getX()));
                // double y = vpos.getY(); // Math.min(HEIGHT/2, Math.max(-HEIGHT/2, vpos.getY()));
                // pos.put(v, new Vector2D(x, y));
                

                totalEnergy += force.mod();
            }
            
            step = cool(step, totalEnergy, oldTotalEnergy);

            if (Math.abs(totalEnergy - oldTotalEnergy) < 1.0)
                break;
            
        }
            

        // Change the vertex positions

        for (Vertex v : verts) {
            
            v.setPos(pos.get(v).getX(), pos.get(v).getY(), 1.0);
        }
        

        //System.out.println("Termination: " + sw.elapsedTime());
        
    }
}
