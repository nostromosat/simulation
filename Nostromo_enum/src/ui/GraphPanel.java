package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1;
	private static double[][] orbit;

	private int width = 800;
	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = Color.blue;
	private Color pointColor = Color.gray;
	private Color backgroundColor = Color.white;
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private List<Double> scores;
	private double[][] values;
	private double xScale = 60;
	private double yScale = 60;

	public GraphPanel(List<Double> scores) {
		this.scores = scores;
		this.values = new double[2][scores.size()];
		for(int i=0;i<this.scores.size();i++){
			values[0][i] = i;
			values[1][i] = this.scores.get(i);
		}
	}
	public GraphPanel(double[][] values){
		this.values = values;
	}
	public GraphPanel(double[][] values, Dimension size){
		this.values = values;
		this.setPreferredSize(size);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//	double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (values[0].length - 1);
		//	double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < values[0].length; i++) {
			int x1 = (int) (values[0][i] * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxScore() - values[1][i]) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}

		// draw background
		g2.setColor(this.backgroundColor);
		g2.fillRect(padding + labelPadding,
				padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);

		// create x and y axes 
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding,
				padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding,
				getWidth() - padding, getHeight() - padding - labelPadding);

		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y + padding;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y + padding;
			g2.drawLine(x1, y1, x2, y2);
		}

		//g2.setStroke(oldStroke);
		g2.setColor(pointColor);
	}

	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		for(int i=0; i < values[0].length; i++){
			if(values[1][i] < minScore) 
				minScore = values[1][i];
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		for (int i=0; i < values[0].length;i++) {
			if(values[1][i] > maxScore)
				maxScore = values[1][i];
		}
		return maxScore;
	}

	public void setScores(List<Double> scores) {
		this.scores = scores;
		invalidate();
		this.repaint();
	}

	public List<Double> getScores() {
		return scores;
	}

	public void setOrbitViewerStyle(){
		this.backgroundColor = Color.black;
		this.lineColor = Color.LIGHT_GRAY;
		this.pointColor = Color.lightGray;
	}

	public static void test(){
		GraphPanel test = new GraphPanel(orbit, new Dimension(800,600));
		test.setOrbitViewerStyle();
		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(test);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		double[][] spiral = new double[2][100];
		double z = 0;
		for(int i=0; i< 100; i++){
			spiral[0][i] = z * Math.cos(z) +5;
			spiral[1][i] = z * Math.sin(z) +5;
			z = z + 0.0625;
		}
		orbit = spiral;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				test();
			}
		});
	}
}