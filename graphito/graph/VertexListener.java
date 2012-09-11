
package graphito.graph;

import graphito.graph.VertexStyleChangeEvent;
import graphito.graph.VertexPosChangeEvent;
import java.util.EventListener;

public interface VertexListener extends EventListener {
	void vertexStyleChanged(VertexStyleChangeEvent v);
	void vertexPosChanged(VertexPosChangeEvent v);
}