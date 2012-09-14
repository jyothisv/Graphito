
package graphito.test;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.*;
//import graphito.graph.layout.HighDimEmbed;

//import graphito.graph.layout.ForceEnergyLayout;
// import graphito.graph.layout.GridLayout;
import graphito.graph.layout.CircularLayout;


public class LayoutTest {

    public static class VertFact implements VertexFactory<Vertex> {
        //private static int count = 0;
        private int count;

        public Vertex createVertex()
        {
            ++count;
            Vertex vert = new Vertex(count+"");
            return vert;
        }
    }


    public static class EdgeFact implements EdgeFactory<Vertex, Edge> {
        //private static int count = 0;
        private int count;

        public Edge createEdge(Vertex u, Vertex v)
        {
            ++count;
            return new Edge(count+"", u, v);

        }
    }

    
    public static void main(String[] args)
    {
        
        
        
        Graph<Vertex, Edge> graph = new SimpleGraph(new EdgeFact());

        int N, M;
        if (args == null) {
            N = 100;
            M = 100;
        } else {
            N = Integer.parseInt(args[0]);

            M = N;
            if (args.length > 1)
                M = Integer.parseInt(args[1]);
        }
        GraphGenerator graphGen = new CompleteGraphGenerator(N);

        
        VertexFactory<Vertex> vfact = new VertFact();

        graphGen.generateGraph(graph, vfact, null);


        CircularLayout.layout(graph);
        // HighDimEmbed.layout(graph);

        for ( Vertex v: graph.vertexSet()) {
            System.out.printf("%f, %f\n", v.getX(), v.getY());
        }

        //        System.out.println(graph);

    }
}
