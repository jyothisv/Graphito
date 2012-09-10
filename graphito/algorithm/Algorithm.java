
package graphito.algorithm;

interface Algorithm extends Runnable {
	Algorithm createAlgorithm();
	void setGraph(Graph<? extends Vertex, ? extends Edge> graph);
}