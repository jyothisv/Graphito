package graphito.algorithm;

import java.awt.Color;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;

import graphito.algorithm.Algorithm;
import graphito.graph.Edge;
import graphito.graph.Vertex;

public class DemoAlgorithm implements Algorithm<Vertex, Edge> {

	private Graph<Vertex, Edge> g;
	private Color c;

	public DemoAlgorithm() {
		c = Color.GREEN;
		g = null;
	}
	
	@Override
	public VertexFactory<Vertex> getVertexFactory() {
		return new Vertex.Factory();
	}

	@Override
	public EdgeFactory<Vertex,Edge> getEdgeFactory() {
		return new Edge.Factory<Vertex>();
	}

	@Override
	public void setGraph(Graph<Vertex,Edge> g) {
		this.g = g;
	}

	@Override
	public void executeSingleStep() {
		for (Vertex v: g.vertexSet()) {
			v.setFillColor(c);
		}
		c = c == Color.GREEN ? Color.BLUE : Color.GREEN;
	}

}