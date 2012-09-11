
package graphito.graph;

import java.awt.AWTEvent;

public class EdgeStyleChangeEvent extends AWTEvent {
	EdgeStyleChangeEvent(Object source) {
		super(source, RESERVED_ID_MAX + 3);
	}
}