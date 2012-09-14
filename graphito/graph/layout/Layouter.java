
package graphito.graph.layout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import org.jgrapht.Graph;

public interface Layouter {
	<V extends Vertex, E extends Edge> void layout(Graph<V, E> graph);
}