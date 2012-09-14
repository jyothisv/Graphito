
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
import graphito.graph.VertexListener;
import graphito.graph.VertexStyleChangeEvent;
import graphito.graph.VertexPosChangeEvent;

public class Vertex implements Shape {
	private Ellipse2D.Double circle;
	private String id;
	private Color fillColor;
	private Color strokeColor;
	private EventListenerList vertexEventList;


	private void fireVertexStyleChangeEvent() {
		VertexStyleChangeEvent ev = new VertexStyleChangeEvent(this);
		for (VertexListener v: vertexEventList.getListeners(VertexListener.class)) {
			v.vertexStyleChanged(ev);
		}
	}

	private void fireVertexPosChangeEvent() {
		VertexPosChangeEvent ev = new VertexPosChangeEvent(this);
		for (VertexListener v: vertexEventList.getListeners(VertexListener.class)) {
			v.vertexPosChanged(ev);
		}
	}

	public void addVertexListner(VertexListener v) {
		vertexEventList.add(VertexListener.class, v);
	}

	public void removeVertexListner(VertexListener v) {
		vertexEventList.remove(VertexListener.class, v);
	}

	public Vertex(String id) {
		this.id = id;
		this.circle = new Ellipse2D.Double();
		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
		vertexEventList = new EventListenerList();
	}

	public String getId() {
		return id;
	}

	public void setStrokeColor(Color c) {
		strokeColor = c;
		fireVertexStyleChangeEvent();
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setFillColor(Color c) {
		fillColor = c;
		fireVertexStyleChangeEvent();
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setPos(double x, double y, double r) {
		circle.setFrame(x, y, r, r);
		fireVertexPosChangeEvent();
	}

	public double getX() {
		return circle.getX();
	}

	public double getY() {
		return circle.getY();
	}

	public double getRadius() {
		return circle.getHeight();
	}

	public boolean contains(double x, double y) {
		return circle.contains(x,y);
	}

	public boolean contains(double x, double y, double w, double h) {
		return circle.contains(x, y, w, h);
	}

	public boolean contains(Rectangle2D r) {
		return circle.contains(r);
	}

	public boolean contains(Point2D p) {
		return circle.contains(p);
	}

	public Rectangle getBounds() {
		return circle.getBounds();
	}

	public Rectangle2D getBounds2D() {
		return circle.getBounds2D();
	}

	public PathIterator getPathIterator(AffineTransform at) {
		return circle.getPathIterator(at);
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return circle.getPathIterator(at, flatness);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return circle.intersects(x, y, w, h);
	}

	public boolean intersects(Rectangle2D r) {
		return circle.intersects(r);
	}
}
