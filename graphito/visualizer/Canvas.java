
package graphito.visualizer;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.jgrapht.Graph;

import graphito.graph.Vertex;
import graphito.graph.Edge;

public interface Canvas {
	void setGraph(Graph<? extends Vertex, ? extends Edge> g);
	void setScale(int sx, int sy);
	void setWindowSize(Dimension d);
	Dimension getDimensions();
	BufferedImage getView();
}