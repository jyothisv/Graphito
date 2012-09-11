
package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;

interface Layouter {
	void layout(Graph<? extends Vertex, ? extends Edge> graph);
}