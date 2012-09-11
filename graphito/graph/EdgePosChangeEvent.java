
package graphito.graph;

import java.awt.AWTEvent;

public class EdgePosChangeEvent extends AWTEvent {
	EdgePosChangeEvent(Object source) {
		super(source, RESERVED_ID_MAX + 4);
	}
}