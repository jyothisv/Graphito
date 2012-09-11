
package graphito.graph;

import java.awt.AWTEvent;

public class VertexPosChangeEvent extends AWTEvent {
	VertexPosChangeEvent(Object source) {
		super(source, RESERVED_ID_MAX + 2);
	}
}