package graphito.graph.layout;

import graphito.graph.layout.Layouter;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.graph.layout.Vector2D;
import org.jgrapht.Graph;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class ForceEnergyLayout implements Layouter {
    private double HEIGHT;
    private double WIDTH;
    private double dampen;
    private int progress;

    public ForceEnergyLayout() {
        HEIGHT = 600;
        WIDTH = 800;
        dampen = 0.9;
        progress = 0;
    }

    private double attraction(double k, Vector2D x)
    {
        return x.mod2()/(k*k);
    }

    private double repulsion(double k, Vector2D x)
    {
        return -k*k/(x.mod()) * 10.0;
    }

    // private Vertex[] adjacent(Graph<Vertex, Edge> graph, Vertex v)
    // {
    //     Vertex[] neigh = new Vertex[graph.edgesOf(v).size()];

    //     int i = 0;
    //     for (Edge e: graph.edgesOf(v)) {
    //         Vertex src = e.getSource();
    //         if (src != v)
    //             neigh[i++] = src;
    //         else neigh[i++] = e.getDest();
    //     }

    //     return neigh;
    // }

    private double cool(double step, double tot, double oldTot)
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

        // if (step > 1.0)
        //     return step*dampen;
        

        if (step > 0.0)
            return step;
        else return 0.0001;
    }
    



    // Implementation of Fruchterman & Reingold's Graph Layout algorithm.

    @Override
    public <V extends Vertex, E extends Edge> void layout(Graph<V, E> graph)
    {
        Set<V> verts = graph.vertexSet();
        Set<E> edges = graph.edgeSet();

        Stopwatch sw = new Stopwatch();
        
        int N = verts.size();

        Random randGen = new Random();
        HashMap<V, Vector2D> pos = new HashMap<V, Vector2D>();


        // Assume that no points are assigned the exactly same positions
        for (Vertex v : verts) {
            pos.put(v, new Vector2D(WIDTH*randGen.nextDouble(), HEIGHT*randGen.nextDouble()));
             
             // System.out.println("Intitial: " + v.getId() + " " + pos.get(v));
        }

        // if N <= 1; Nothing more to do
        if (N <= 1)
            return;
        
        // System.out.println("Finished Initialization at: " + sw.elapsedTime());
        
        double k = Math.sqrt(WIDTH*HEIGHT/N);
        // double t = 1; //Math.min(WIDTH, HEIGHT)/3.0;
        double step =  Math.min(WIDTH, HEIGHT)/3.0;
        // double step = 1.0;
        double totalEnergy = 1.0/0.0; // Infinity
        double oldTotalEnergy = 0.0;

        Vector2D force = new Vector2D();
        Vector2D delta = new Vector2D();
        
        for (int i = 0; i < 1000; ++i) {
            // System.out.printf("%d th round\n", i);



            // System.out.println(sw.elapsedTime());
            oldTotalEnergy = totalEnergy;

            
            totalEnergy = 0.0;
            
            for (V v: verts) {

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

                
                for (V u: verts) {
                    if (v != u) {
                        pos.get(u).minus(pos.get(v), delta);
                        
                        double repulse = repulsion(k, delta);
                        force.add(delta.norm().mult(repulse));
                    }
                }
                

                Vector2D vpos = pos.get(v);


                totalEnergy += force.mod();

                
                vpos.add(force.norm().mult(step)); // .norm().mult(Math.min(t, force.mod())));     // Math.min(disp.mod(), t)));

                //Vector2D delta = vpos2.minus(vpos);

                // System.out.println("Final delta = " + delta);
                
                // double x = vpos.getX(); // Math.min(WIDTH/2, Math.max(-WIDTH/2, vpos.getX()));
                // double y = vpos.getY(); // Math.min(HEIGHT/2, Math.max(-HEIGHT/2, vpos.getY()));
                // pos.put(v, new Vector2D(x, y));
                

            }
            
            step = cool(step, totalEnergy, oldTotalEnergy);
            
            if (Math.abs(totalEnergy - oldTotalEnergy) < 0.01)
                break;
            
            // System.out.printf("loop var = %d, Energy difference = %f; New step = %f\n", i, totalEnergy - oldTotalEnergy, step);

            
        }
            

        double MinX = 1.0/0, MinY = 1.0/0;
        // double MaxX = 1.0/0, MaxY = 1.0/0;
        // Determine MinX, MinY, MaxX and MaxY
        Vector2D p;
        for (V v: verts) {
            p = pos.get(v);
            if (p.getX() < MinX)
                MinX = p.getX();
            // else if (p.getX() > MaxX)
            //     MaxX = p.getX();

            if (p.getY() < MinY)
                MinY = p.getY();
            // else if (p.getY() > MaxY)
            //     MaxY = p.getY();
        }
                
        
        // Change the vertex positions
        MinX = Math.max(0.0, -MinX);
        MinY = Math.max(0.0, -MinY);
        
        for (V v : verts) {           
            v.setPos(pos.get(v).getX() + MinX, pos.get(v).getY() + MinY, 3.0);
        }
        

        // Compute the min distance b/w points

        double minDist = 1.0/0;

        Vertex[] vertsArray = verts.toArray(new Vertex[N]);
        for (int i = 0; i < N; ++i)
            for (int j = i + 1; j < N; ++j)
                if (minDist > distance2(vertsArray[i], vertsArray[j]))
                    minDist = distance2(vertsArray[i], vertsArray[j]);

        //System.out.printf("Min Distance = %f, Energy difference = %f\n", minDist, totalEnergy - oldTotalEnergy);

        // System.out.println("Termination: " + sw.elapsedTime());
        


    }


    private static double distance2(Vertex u, Vertex v)
    {
        double x = u.getX() - v.getX();
        double y = u.getY() - v.getY();

        return x*x + y*y;
    }
}

