package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;
import java.util.Random;
import java.util.Set;
import java.util.Hashtable;


class ForceLayout {
    private static double HEIGHT = 600;
    private static double WIDTH = 800;
    private static double dampen = 0.8;


    private static double attraction(double k, double x)
    {
        return x*x/k;
    }

    private static double repulsion(double k, double x)
    {
        return k*k/x;
    }



    
    public static void layout(Graph<? extends Vertex, ? extends Edge> graph)
    {
        Set<? extends Vertex> verts = graph.vertexSet();
        Set<? extends Edge> edges = graph.edgeSet();
        
        int N = verts.size();

        Random randGen = new Random();
        
        // double [] velocity = double[N];
        // Assume that velocities are initialized to zero
        
        // double [] xpos = xpos[N];
        // double [] ypos = ypos[N];

        Hashtable<Vertex,Double> xpos = new Hashtable<Vertex, Double>();
        Hashtable<Vertex,Double> ypos = new Hashtable<Vertex, Double>();

        Hashtable<Vertex,Double> displace = new Hashtable<Vertex, Double>();
        
        // Assume that no points are assigned the exactly same positions
        for (Vertex v : verts) {
            xpos.put(v, randGen.nextDouble());
            ypos.put(v, randGen.nextDouble());
        }

        double k = Math.sqrt(WIDTH*HEIGHT/N);
        double t = Math.min(WIDTH, HEIGHT)/3;

        
        while (true) {
            //kineticEnergy = 0;

            for (Vertex u: verts) {
                
                displace.put(u, 0.0);
                             
                for (Vertex v: verts) {
                    if (v != u) {
                        double delta = displace.get(u) - displace.get(v);
                        double newDisp = displace.get(u) +
                            Math.signum(delta)*repulsion(k, Math.abs(delta));
                        displace.put(u, newDisp);
                    }
                }
            }

            for (Edge e: edges) {
                Vertex src = e.getSource();
                Vertex dst = e.getDest();

                double delta = displace.get(src) - displace.get(dst);

                double srcDisp = displace.get(src) -
                    Math.signum(delta)*attraction(k, Math.abs(delta));
                double dstDisp = displace.get(dst) +
                    Math.signum(delta)*attraction(k, Math.abs(delta));

                displace.put(src, srcDisp);
                displace.put(dst, dstDisp);

            }


            for (Vertex v: verts) {
                double x = xpos.get(v);
                double y = ypos.get(v);
                double disp = displace.get(v);
                    
                // Update x and y
                x = x + Math.signum(disp)*Math.min(disp, t);
                y = y + Math.signum(disp)*Math.min(disp, t);
                    
                x = Math.min(WIDTH/2,
                             Math.max(-WIDTH/2, x));
                y = Math.min(HEIGHT/2,
                             Math.max(-HEIGHT/2, y));

            }

            t *= dampen;

            if ( t < 0.01 )
                break;
            
        }

        // Change the vertex positions

        for (Vertex v : verts) {
            
            v.setPos(xpos.get(v)*WIDTH, ypos.get(v)*HEIGHT, 1.0);
        }
        
            
    }
}
