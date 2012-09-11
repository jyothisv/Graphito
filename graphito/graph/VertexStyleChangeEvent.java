
package graphito.graph;

import java.awt.AWTEvent;

public class VertexStyleChangeEvent extends AWTEvent {
	VertexStyleChangeEvent(Object source) {
		super(source, RESERVED_ID_MAX + 1);
	}
}