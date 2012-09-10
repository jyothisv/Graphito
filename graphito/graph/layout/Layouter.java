
package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;

interface Layouter {
	void layout(Graph<V extends Vertex, E extends Edge> graph);
}