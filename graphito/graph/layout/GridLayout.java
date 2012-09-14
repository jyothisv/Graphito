package graphito.graph.layout;

import graphito.graph.layout.Layouter;

import java.util.Set;
import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.graph.layout.Vector2D;
import org.jgrapht.Graph;
// import graphito.graph.layout.Stopwatch;

public class GridLayout implements  Layouter {

    @Override
    public <V extends Vertex, E extends Edge> void layout(Graph<V, E> graph)
    {
        // Stopwatch sw = new Stopwatch();        
        Set<V> verts = graph.vertexSet();

        int N = verts.size();

        double step = 100;
        double width = step*Math.sqrt(N);
        
        double x = 0, y = 0;

        for (V v: verts) {
            v.setPos(x, y, 3.0);

            x += step;

            if (x > width) {
                x = 0.0;
                y += step;
            }
        }

    

        // System.out.println("Termination: " + sw.elapsedTime());

    //     double minDist = 1.0/0;

    //     Vertex[] vertsArray = verts.toArray(new Vertex[N]);
    //     for (int i = 0; i < N; ++i)
    //         for (int j = i + 1; j < N; ++j)
    //             if (minDist > distance2(vertsArray[i], vertsArray[j]))
    //                 minDist = distance2(vertsArray[i], vertsArray[j]);

    //     System.out.printf("Min Distance = %f\n", minDist);

    // }

    // private static double distance2(Vertex u, Vertex v)
    // {
    //     double x = u.getX() - v.getX();
    //     double y = u.getY() - v.getY();

    //     return x*x + y*y;
    // }


}
            
