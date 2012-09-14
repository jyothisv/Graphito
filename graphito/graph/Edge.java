
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


	private void fireEdgeStyleChangeEvent() {
		EdgeStyleChangeEvent ev = new EdgeStyleChangeEvent(this);
		for (EdgeListener v: edgeEventList.getListeners(EdgeListener.class)) {
			v.edgeStyleChanged(ev);
		}
	}

	private void fireEdgePosChangeEvent() {
		EdgePosChangeEvent ev = new EdgePosChangeEvent(this);
		for (EdgeListener v: edgeEventList.getListeners(EdgeListener.class)) {
			v.edgePosChanged(ev);
		}
	}

	public void addEdgeListner(EdgeListener v) {
		edgeEventList.add(EdgeListener.class, v);
	}

	public void removeEdgeListner(EdgeListener v) {
		edgeEventList.remove(EdgeListener.class, v);
	}

	public Edge(String id, Vertex source, Vertex dest) {
		this.id = id;
		strokeColor = Color.BLACK;
		this.source = source;
		this.dest = dest;
		line = new Line2D.Double(source.getX(), source.getY(), dest.getX(), dest.getY());
	}

	public String getId() {
		return id;
	}

	public void setStrokeColor(Color c) {
		strokeColor = c;
		fireEdgeStyleChangeEvent();
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getDest() {
		return dest;
	}

	@Override
	public void vertexPosChanged(VertexPosChangeEvent e) {
		line = new Line2D.Double(source.getX(), source.getY(), dest.getX(), dest.getY());
		fireEdgePosChangeEvent();
	}

	@Override
	public void vertexStyleChanged(VertexStyleChangeEvent e) {

	}

	public boolean contains(double x, double y) {
		return line.contains(x,y);
	}

	public boolean contains(double x, double y, double w, double h) {
		return line.contains(x, y, w, h);
	}

	public boolean contains(Rectangle2D r) {
		return line.contains(r);
	}

	public boolean contains(Point2D p) {
		return line.contains(p);
	}

	public Rectangle getBounds() {
		return line.getBounds();
	}

	public Rectangle2D getBounds2D() {
		return line.getBounds2D();
	}

	public PathIterator getPathIterator(AffineTransform at) {
		return line.getPathIterator(at);
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return line.getPathIterator(at, flatness);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return line.intersects(x, y, w, h);
	}

	public boolean intersects(Rectangle2D r) {
		return line.intersects(r);
	}
}
