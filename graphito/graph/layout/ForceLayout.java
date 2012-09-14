package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.graph.layout.Vector2D;
import org.jgrapht.Graph;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;


public class ForceLayout {
    private static double HEIGHT = 600;
    private static double WIDTH = 800;
    private static double dampen = 0.001;


    private static double attraction(double k, Vector2D x)
    {
        return x.mod2()/k;
    }

    private static double repulsion(double k, Vector2D x)
    {
        // if (x == 0)
        //     return 1000;
        return k*k/(x.mod());
    }




    // Implementation of Fruchterman & Reingold's Graph Layout algorithm.
    
    public static void layout(Graph<? extends Vertex, ? extends Edge> graph)
    {
        Set<? extends Vertex> verts = graph.vertexSet();
        Set<? extends Edge> edges = graph.edgeSet();
        
        int N = verts.size();

        Random randGen = new Random();

        HashMap<Vertex, Vector2D> pos = new HashMap<Vertex, Vector2D>();

        HashMap<Vertex, Vector2D> displace = new HashMap<Vertex, Vector2D>();

        // Assume that no points are assigned the exactly same positions
        for (Vertex v : verts) {
            pos.put(v, new Vector2D(randGen.nextDouble(), randGen.nextDouble()));
        }

        double k = Math.sqrt(WIDTH*HEIGHT/N);
        double t = 1; //Math.min(WIDTH, HEIGHT)/3.0;


        for (int i = 0; i < 1000; ++i) {
            //Vector2D totalDisp = new Vector2D(0.0, 0.0);
            
            for (Vertex v: verts) {
                
                displace.put(v, new Vector2D(0.0, 0.0));
                
                for (Vertex u: verts) {
                    if (v != u) {
                        Vector2D delta = pos.get(v).minus(pos.get(u));

                        // double magn = delta.mod();
                        Vector2D newDisp = displace.get(v).add(delta.norm().mult(repulsion(k, delta)));

                        //System.out.println("delta = " + delta + " newDisp = " + newDisp);
                        displace.put(v, newDisp);
                    }
                }
            }

            for (Edge e: edges) {
                Vertex src = e.getSource();
                Vertex dst = e.getDest();

                Vector2D delta = pos.get(src).minus(pos.get(dst));

                // double magn = delta.mod();
                Vector2D srcDisp = displace.get(src).minus(delta.norm().mult(attraction(k, delta)));

                Vector2D dstDisp = displace.get(dst).add(delta.norm().mult(attraction(k, delta)));

                //System.out.println("delta = " + delta + " srcDisp = " + srcDisp + " dstDisp = " + dstDisp);

                displace.put(src, srcDisp);
                displace.put(dst, dstDisp);
            }


            for (Vertex v: verts) {
                Vector2D vpos = pos.get(v);

                Vector2D disp = displace.get(v);

                //System.out.println("Effective Disp = " + disp);

                double magnVpos = vpos.mod();

                // if (totalDisp < disp.mod())
                //     totalDisp = disp.mod();
                //  totalDisp = totalDisp.add(disp.div(disp.mod()));
                
                // Update x and y
                vpos = vpos.add(disp.norm().mult(Math.min(disp.mod(), t)));     // Math.min(disp.mod(), t)));
                
                double x = vpos.getX(); //Math.min(WIDTH/2,   Math.max(-WIDTH/2, vpos.getX()));
                double y = vpos.getY(); // Math.min(HEIGHT/2, Math.max(-HEIGHT/2, vpos.getY()));
                pos.put(v, new Vector2D(x, y));

            }

            //System.out.printf("total disp = %s\n", totalDisp.toString());
            
            // if ( totalDisp/N < t ) {
            //     System.out.println("dampening to " + t*dampen);
            //     t*=dampen;
            // }

            // System.out.printf("Average disp = %f\n", totalDisp);
            
            // if (t < 0.5)
            //     break;
        }
        

        // Change the vertex positions

        for (Vertex v : verts) {
            
            v.setPos(pos.get(v).getX(), pos.get(v).getY(), 1.0);
        }
        
            
    }
}
