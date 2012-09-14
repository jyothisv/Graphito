
package graphito.graph;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import javax.swing.event.EventListenerList;
import java.awt.Color;

import org.jgrapht.VertexFactory;

import graphito.graph.VertexListener;
import graphito.graph.VertexStyleChangeEvent;
import graphito.graph.VertexPosChangeEvent;

public class Vertex implements Shape {
	private Ellipse2D.Double circle;
	private String id;
	private Color fillColor;
	private Color strokeColor;
	private EventListenerList vertexEventList;

	public static class Factory implements VertexFactory<Vertex> {
		private int count;

		public Factory() {
			count = 0;
		}

		@Override
		public Vertex createVertex() {
			return new Vertex(String.format("v%d", count++));
		}
	}

	@Override
	public String toString() {
		return String.format("Vertex(%s, %f, %f, %f)", id, circle.getX(), circle.getY(), circle.getHeight());
	}


	private final void fireVertexStyleChangeEvent() {
		VertexStyleChangeEvent ev = new VertexStyleChangeEvent(this);
		for (VertexListener v: vertexEventList.getListeners(VertexListener.class)) {
			v.vertexStyleChanged(ev);
		}
	}

	private final void fireVertexPosChangeEvent() {
		VertexPosChangeEvent ev = new VertexPosChangeEvent(this);
		for (VertexListener v: vertexEventList.getListeners(VertexListener.class)) {
			v.vertexPosChanged(ev);
		}
	}

	public final void addVertexListener(VertexListener v) {
		vertexEventList.add(VertexListener.class, v);
	}

	public final void removeVertexListener(VertexListener v) {
		vertexEventList.remove(VertexListener.class, v);
	}

	protected Vertex(String id) {
		this.id = id;
		this.circle = new Ellipse2D.Double();
		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
		vertexEventList = new EventListenerList();
	}

	public final String getId() {
		return id;
	}

	public final void setStrokeColor(Color c) {
		strokeColor = c;
		fireVertexStyleChangeEvent();
	}

	public final Color getStrokeColor() {
		return strokeColor;
	}

	public final void setFillColor(Color c) {
		fillColor = c;
		fireVertexStyleChangeEvent();
	}

	public final Color getFillColor() {
		return fillColor;
	}

	public final void setPos(double x, double y, double r) {
		circle.setFrame(x, y, r, r);
		fireVertexPosChangeEvent();
	}

	public final double getX() {
		return circle.getX();
	}

	public final double getY() {
		return circle.getY();
	}

	public final double getRadius() {
		return circle.getHeight();
	}

	public final boolean contains(double x, double y) {
		return circle.contains(x,y);
	}

	public final boolean contains(double x, double y, double w, double h) {
		return circle.contains(x, y, w, h);
	}

	public final boolean contains(Rectangle2D r) {
		return circle.contains(r);
	}

	public final boolean contains(Point2D p) {
		return circle.contains(p);
	}

	public final Rectangle getBounds() {
		return circle.getBounds();
	}

	public final Rectangle2D getBounds2D() {
		return circle.getBounds2D();
	}

	public final PathIterator getPathIterator(AffineTransform at) {
		return circle.getPathIterator(at);
	}

	public final PathIterator getPathIterator(AffineTransform at, double flatness) {
		return circle.getPathIterator(at, flatness);
	}

	public final boolean intersects(double x, double y, double w, double h) {
		return circle.intersects(x, y, w, h);
	}

	public final boolean intersects(Rectangle2D r) {
		return circle.intersects(r);
	}
}