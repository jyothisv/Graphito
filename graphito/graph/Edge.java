
package graphito.graph;

import java.awt.geom.Line2D;
import java.awt.Shape;
import javax.swing.event.EventListenerList;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import org.jgrapht.EdgeFactory;

import graphito.graph.VertexListener;
import graphito.graph.VertexStyleChangeEvent;
import graphito.graph.VertexPosChangeEvent;
import graphito.graph.EdgeListener;
import graphito.graph.EdgeStyleChangeEvent;
import graphito.graph.EdgePosChangeEvent;
import graphito.graph.Vertex;

public class Edge implements VertexListener, Shape {
	private String id;
	private Color strokeColor;
	private Vertex source, dest;
	private Line2D.Double line;
	private EventListenerList edgeEventList;

	public static class Factory<V extends Vertex> implements EdgeFactory<V,Edge> {
		private int count;

		public Factory() {
			count = 0;
		}

		@Override
		public Edge createEdge(V source, V dest) {
			return new Edge(String.format("Edge%d: (%s, %s)", count++, source.getId(), dest.getId()), source, dest);
		}
	}

	@Override
	public String toString() {
		return String.format("Edge(%s: (%s, %s))", id, source.getId(), dest.getId());
	}


	private final void fireEdgeStyleChangeEvent() {
		EdgeStyleChangeEvent ev = new EdgeStyleChangeEvent(this);
		for (EdgeListener v: edgeEventList.getListeners(EdgeListener.class)) {
			v.edgeStyleChanged(ev);
		}
	}

	private final void fireEdgePosChangeEvent() {
		EdgePosChangeEvent ev = new EdgePosChangeEvent(this);
		for (EdgeListener v: edgeEventList.getListeners(EdgeListener.class)) {
			v.edgePosChanged(ev);
		}
	}

	public final void addEdgeListener(EdgeListener v) {
		edgeEventList.add(EdgeListener.class, v);
	}

	public final void removeEdgeListener(EdgeListener v) {
		edgeEventList.remove(EdgeListener.class, v);
	}

	protected Edge(String id, Vertex source, Vertex dest) {
		this.id = id;
		strokeColor = Color.BLACK;
		this.source = source;
		this.dest = dest;
		edgeEventList = new EventListenerList();
		line = new Line2D.Double(source.getX() + source.getBounds().getWidth()/2.0, 
			                     source.getY() + source.getBounds().getHeight()/2.0,
			                     dest.getX() + dest.getBounds().getWidth()/2.0, 
			                     dest.getY() + dest.getBounds().getHeight()/2.0);
		source.addVertexListener(this);
		dest.addVertexListener(this);
	}

	public final String getId() {
		return id;
	}

	public final void setStrokeColor(Color c) {
		strokeColor = c;
		fireEdgeStyleChangeEvent();
	}

	public final Color getStrokeColor() {
		return strokeColor;
	}

	public final Vertex getSource() {
		return source;
	}

	public final Vertex getDest() {
		return dest;
	}

	@Override
	public final void vertexPosChanged(VertexPosChangeEvent e) {
		line = new Line2D.Double(source.getX() + source.getBounds().getWidth()/2.0, 
			                     source.getY() + source.getBounds().getHeight()/2.0,
			                     dest.getX() + dest.getBounds().getWidth()/2.0, 
			                     dest.getY() + dest.getBounds().getHeight()/2.0);
		fireEdgePosChangeEvent();
	}

	@Override
	public final void vertexStyleChanged(VertexStyleChangeEvent e) {

	}

	public final boolean contains(double x, double y) {
		return line.contains(x,y);
	}

	public final boolean contains(double x, double y, double w, double h) {
		return line.contains(x, y, w, h);
	}

	public final boolean contains(Rectangle2D r) {
		return line.contains(r);
	}

	public final boolean contains(Point2D p) {
		return line.contains(p);
	}

	public final Rectangle getBounds() {
		return line.getBounds();
	}

	public final Rectangle2D getBounds2D() {
		return line.getBounds2D();
	}

	public final PathIterator getPathIterator(AffineTransform at) {
		return line.getPathIterator(at);
	}

	public final PathIterator getPathIterator(AffineTransform at, double flatness) {
		return line.getPathIterator(at, flatness);
	}

	public final boolean intersects(double x, double y, double w, double h) {
		return line.intersects(x, y, w, h);
	}

	public final boolean intersects(Rectangle2D r) {
		return line.intersects(r);
	}
}