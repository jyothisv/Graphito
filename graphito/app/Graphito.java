
package graphito.app;

import java.awt.Dimension;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.ArrayList;
import java.util.Random;

import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.EdgeFactory;

import net.miginfocom.swing.MigLayout;

import graphito.graph.Vertex;
import graphito.graph.Edge;
import graphito.algorithm.Algorithm;
import graphito.algorithm.DemoAlgorithm;
import graphito.visualizer.GraphVisualizer;
import graphito.visualizer.SimpleCanvas;
import graphito.visualizer.Canvas;
import graphito.graph.layout.ForceEnergyLayout;

public class Graphito extends JFrame {

	private JButton startRun;
	private JButton stopRun;
	private JSlider beatSpeed;
	private Timer beatTimer;
	private JButton singleStep;
	private GraphVisualizer gv;
	private ArrayList<Algorithm<? extends Vertex, ? extends Edge>> algs;

	private class StartListener implements ActionListener {
		@Override
		public void	actionPerformed(ActionEvent e) {
			Graphito.this.startRun.setEnabled(false);
			Graphito.this.singleStep.setEnabled(false);
			Graphito.this.beatTimer.setDelay(Graphito.this.beatSpeed.getValue());
			Graphito.this.beatTimer.start();
			Graphito.this.stopRun.setEnabled(true);
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void	actionPerformed(ActionEvent e) {
			if(Graphito.this.beatTimer.isRunning()) {
				Graphito.this.beatTimer.stop();
				Graphito.this.singleStep.setEnabled(true);
				Graphito.this.startRun.setEnabled(true);
				Graphito.this.stopRun.setEnabled(false);
			}
		}
	}

	private class BeatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Algorithm<? extends Vertex, ? extends Edge>> finLst = new ArrayList<>();

			for (Algorithm<? extends Vertex, ? extends Edge> a: Graphito.this.algs) {
				if(a.executeSingleStep() == false) {
					finLst.add(a);
				}
			}

			for (Algorithm<? extends Vertex, ? extends Edge> a : finLst) {
				Graphito.this.algs.remove(a);
			}

			Graphito.this.gv.updateComposite();

			if(Graphito.this.algs.isEmpty()) {
				Graphito.this.beatTimer.stop();
				Graphito.this.singleStep.setEnabled(true);
				Graphito.this.startRun.setEnabled(true);
				Graphito.this.stopRun.setEnabled(false);
			}
		}
	}

	private class SingleStepListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Algorithm<? extends Vertex, ? extends Edge>> finLst = new ArrayList<>();

			for (Algorithm<? extends Vertex, ? extends Edge> a: Graphito.this.algs) {
				if(a.executeSingleStep() == false) {
					finLst.add(a);
				}
			}

			for (Algorithm<? extends Vertex, ? extends Edge> a : finLst) {
				Graphito.this.algs.remove(a);
			}

			Graphito.this.gv.updateComposite();
		}
	}

	private class BeatSpeedListener implements ChangeListener {
		@Override
		public void	stateChanged(ChangeEvent e) {
			if(Graphito.this.beatTimer.isRunning()) {
				Graphito.this.beatTimer.stop();
				Graphito.this.beatTimer.setDelay(Graphito.this.beatSpeed.getValue());
				Graphito.this.beatTimer.start();
			}
		}
	}


	private Graphito() {
		super("Graphito");
		algs = new ArrayList<>();

		setSize(800, 600);
		setupComponents();

		DemoAlgorithm alg = new DemoAlgorithm();
		setupVisualizer(alg);
		algs.add(alg);
	} 

	private void setupComponents() {
		startRun = new JButton("Start");
		stopRun = new JButton("Stop");
		singleStep = new JButton("Single Step");
		beatSpeed = new JSlider(10, 1000, 500);
		beatTimer = new Timer(beatSpeed.getValue(), new BeatListener());
		gv = new GraphVisualizer();

		gv.setSize(400, 400);
		stopRun.setEnabled(false);

		startRun.addActionListener(new StartListener());
		stopRun.addActionListener(new StopListener());
		singleStep.addActionListener(new SingleStepListener());
		beatSpeed.addChangeListener(new BeatSpeedListener());

		this.setLayout(new MigLayout());
		this.add(startRun);
		this.add(stopRun);
		this.add(singleStep);
		this.add(beatSpeed, "wrap");
		this.add(gv, "span,w 400:100%:100%,h 400:100%:100%");
	}

	private <V extends Vertex, E extends Edge> void setupVisualizer(Algorithm<V,E> alg) {
		EdgeFactory<V,E> ef = alg.getEdgeFactory();
		VertexFactory<V> vf = alg.getVertexFactory();
		Graph<V, E> g = new SimpleGraph<>(ef);
		CompleteGraphGenerator<V,E> gen = new CompleteGraphGenerator<>(500);
		gen.generateGraph(g, vf, null);

		// Do Layout
		ForceEnergyLayout fel = new ForceEnergyLayout();
		fel.layout(g);
		// Random r = new Random();
		// for (Vertex v: g.vertexSet()) {
		// 	v.setPos(r.nextInt(800), r.nextInt(600), 5);
		// }

		Canvas c = new SimpleCanvas();
		c.setGraph(g);
		c.setScale(1, 1);

		gv.addCanvas(c);
		alg.setGraph(g);
	}

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			@Override
			public void	run() {
				JFrame f = new Graphito();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		};

		SwingUtilities.invokeLater(r);
	}
}