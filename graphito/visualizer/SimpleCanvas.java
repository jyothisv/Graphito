
package graphito.visualizer;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;

import org.jgrapht.Graph;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.visualizer.Canvas;

public class SimpleCanvas implements Canvas {

	private Graph<? extends Vertex, ? extends Edge> graph;
	private int sx, sy;
	private Dimension windowSize;
	private Dimension canvasSize;
	private BufferedImage img;

	public SimpleCanvas() {
		graph = null;
		sx = sy = 1;
		img = null;
		windowSize = new Dimension();
	}

	@Override
	public Dimension getDimensions() {
		return canvasSize;
	}

	@Override
	public void setGraph(Graph<? extends Vertex, ? extends Edge> g) {
		this.graph = g;
		Rectangle r = new Rectangle();

		for (Vertex v: g.vertexSet()) {
			r = r.union(v.getBounds());
		}
		canvasSize = new Dimension((int) Math.ceil(r.getWidth()) + 20, (int) Math.ceil(r.getHeight()) + 20);
	}

	@Override
	public void setScale(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	public void setWindowSize(Dimension d) {
		this.windowSize.setSize(d);
		if(d.height * d.width == 0)
			return;
		this.img = new BufferedImage((int) windowSize.getWidth(), (int) windowSize.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}

	@Override
	public BufferedImage getView() {
		if(img == null) {
			return img;
		}

		Graphics2D gpcs = (Graphics2D) img.getGraphics();
		gpcs.scale(windowSize.getWidth()/canvasSize.getWidth(), 
			windowSize.getHeight()/canvasSize.getHeight());

		gpcs.setColor(Color.WHITE);
		gpcs.fillRect(0, 0, (int) canvasSize.getWidth(), (int) canvasSize.getHeight());

		for (Edge e : graph.edgeSet()) {
			if(e.isEnabled()) {
				gpcs.setColor(e.getStrokeColor());
				gpcs.draw(e);
			}
		}

		for (Vertex v: graph.vertexSet()) {
			if(v.isEnabled()) {
				gpcs.setColor(v.getStrokeColor());
				gpcs.draw(v);
				gpcs.setColor(v.getFillColor());
				gpcs.fill(v);
			}
		}

		return img;
	}
}