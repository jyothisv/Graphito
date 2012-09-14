
package graphito.visualizer;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Dimension;

import graphito.visualizer.Canvas;

public class GraphVisualizer extends JPanel implements ComponentListener {

	private ArrayList<Canvas> cvsList;
	private Image compositeImg;
	private Dimension preferredSize;

	public GraphVisualizer() {
		super();
		cvsList = new ArrayList<>();
		preferredSize = new Dimension(10, 10);
		compositeImg = null;
		addComponentListener(this);
	}

	public void addCanvas(Canvas c) {
		cvsList.add(c);
		preferredSize = c.getDimensions();
		c.setWindowSize(getSize());
		updateComposite();
	}

	public void updateComposite() {
		for (Canvas c: cvsList) {
			compositeImg = c.getView();
		}

		repaint();
	}

	@Override
	public void	paint(Graphics g) {
		if(compositeImg != null) {
			g.drawImage(compositeImg, 0, 0, Color.WHITE, null);
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void	componentResized(ComponentEvent e) {
		for (Canvas c: cvsList) {
			c.setWindowSize(this.getSize());
		}
		updateComposite();
	}

	@Override
	public void componentShown(ComponentEvent e) {

	}
}