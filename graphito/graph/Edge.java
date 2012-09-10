
package graphito.graph;

public class Edge {
	private String id;
	private Color strokeColor;
	private Vertex source, dest;

	protected Edge(String id, Vertex source, Vertex dest) {
		this.id = id;
		strokeColor = Color.BLACK;
		this.source = source;
		this.dest = dest;
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

	public Vertex getSource() {
		return source;
	}

	public Vertex getDest() {
		return dest;
	}
}