
package graphito.test;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.*;
import graphito.graph.layout.ForceEnergyLayout;

public class LayoutTest {

    public static void main(String[] args)
    {
        Graph<Vertex, Edge> graph = new SimpleGraph<>(new Edge.Factory<Vertex>());

        int N = Integer.parseInt(args[0]);
        CompleteGraphGenerator<Vertex, Edge> graphGen = new CompleteGraphGenerator<>(N);

        
        VertexFactory<Vertex> vfact = new Vertex.Factory();

        graphGen.generateGraph(graph, vfact, null);

        ForceEnergyLayout fel = new ForceEnergyLayout();
        fel.layout(graph);

        for ( Vertex v: graph.vertexSet()) {
            System.out.printf("%f, %f\n", v.getX(), v.getY());
        }

        System.out.println(graph);

    }
}
