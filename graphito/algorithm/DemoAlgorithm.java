package graphito.algorithm;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;

import graphito.algorithm.Algorithm;
import graphito.graph.Edge;
import graphito.graph.Vertex;

public class DemoAlgorithm implements Algorithm<DemoAlgorithm.myVertex, DemoAlgorithm.myEdge> {

	private Graph<myVertex, myEdge> g;
	private Color c;
	private myVertex currentPlace;

	public DemoAlgorithm() {
		c = Color.GREEN;
		g = null;
		currentPlace = null;
	}

	public static class myVertex extends Vertex {
		public boolean visited;

		private myVertex(String id) {
			super(id);
			visited = false;
		}

		public static class Factory implements VertexFactory<myVertex> {

			private int count = 0;

			public Factory() {
				count = 0;
			}

			@Override
			public myVertex createVertex() {
				return new myVertex(String.format("myVertex %d", count++));
			}
		}
	}

	public static class myEdge extends Edge {
		public int cost;

		private myEdge(String id, Vertex s, Vertex d) {
			super(id, s, d);
			cost = 0;
		}

		public static class Factory<V extends Vertex> implements EdgeFactory<V, myEdge> {

			private int count = 0;

			public Factory() {
				count = 0;
			}

			@Override
			public myEdge createEdge(V s, V d) {
				return new myEdge(String.format("myEdge %d", count++), s, d);
			}
		}
	}
	
	@Override
	public VertexFactory<myVertex> getVertexFactory() {
		return new myVertex.Factory();
	}

	@Override
	public EdgeFactory<myVertex,myEdge> getEdgeFactory() {
		return new myEdge.Factory<myVertex>();
	}

	@Override
	public void setGraph(Graph<myVertex,myEdge> g) {
		this.g = g;

		Random r = new Random();
		for (myEdge e: g.edgeSet()) {
			e.setEnabled(false);
			e.cost = (int) Math.sqrt(Math.pow(e.getDest().getX()-e.getSource().getX(), 2) + Math.pow(e.getDest().getY()-e.getSource().getY(), 2));
		}

		for (myVertex v: g.vertexSet()) {
			if(currentPlace == null)
				currentPlace = v;
			v.setFillColor(Color.BLACK);
		}

		currentPlace.setFillColor(Color.GREEN);
		currentPlace.visited = true;
	}

	@Override
	public boolean executeSingleStep() {
		Set<myEdge> eds = g.edgesOf(currentPlace);
		myVertex nextPlace = null;
		myEdge ed = null;
		int minCost = Integer.MAX_VALUE;

		for (myEdge e: eds) {
			if (e.cost < minCost) {
				myVertex n = null;
				if(g.getEdgeTarget(e) == currentPlace)
					n = g.getEdgeSource(e);
				else
					n = g.getEdgeTarget(e);
				if(!n.visited) {
					nextPlace = n;
					ed = e;
					minCost = e.cost;
				}
			}
		}

		if(nextPlace != null) {
			currentPlace = nextPlace;
			currentPlace.setFillColor(Color.GREEN);
			currentPlace.visited = true;
			ed.setEnabled(true);
			ed.setStrokeColor(Color.BLACK);
		}

		return nextPlace != null;

	}
}