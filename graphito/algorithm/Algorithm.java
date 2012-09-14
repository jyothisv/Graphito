
package graphito.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;

import graphito.graph.Vertex;
import graphito.graph.Edge;

public interface Algorithm<V extends Vertex, E extends Edge> {
	VertexFactory<V> getVertexFactory();
	EdgeFactory<V,E> getEdgeFactory();
	void setGraph(Graph<V,E> g);
	boolean executeSingleStep();
}