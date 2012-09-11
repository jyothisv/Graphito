
package graphito.graph;

import graphito.graph.EdgeStyleChangeEvent;
import graphito.graph.EdgePosChangeEvent;
import java.util.EventListener;

public interface EdgeListener extends EventListener {
	void edgeStyleChanged(EdgeStyleChangeEvent e);
	void edgePosChanged(EdgePosChangeEvent e);
}