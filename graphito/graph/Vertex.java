
package graphito.graph;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

// graffiti
// graphitee

public class Vertex implements Shape {
	private Ellipse2D.Double circle;
	private String id;
	private Color fillColor;
	private Color strokeColor;

	protected Vertex(String id) {
		this.id = id;
		this.circle = new Ellipse2D.Double();
		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
	}

	public String getId() {
		return id;
	}

	public void setStrokeColor(Color c) {
		strokeColor = c;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setFillColor(Color c) {
		fillColor = c;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setPos(double x, double y, double r) {
		circle.setFrame(x, y, r, r);
	}

	public double getX() {
		return circle.getX();
	}

	public double getY() {
		return circle.getY();
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