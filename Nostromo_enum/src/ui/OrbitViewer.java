package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class OrbitViewer extends JPanel{

	private static final long serialVersionUID = 1;
	private int width = 500;
	private int height = 300;
	private double[][] values;

	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = Color.white;
	private Color backgroundColor = Color.BLACK;
	private Color pointColor = Color.white;
	private Color gridColor = Color.BLACK;
	private int pointWidth = 4;
	private int nYDivisions = 10;
	private double xScale = 4;
	private double yScale = 5;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (values[0].length - 1);
		this.yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		/** Background **/
		g2.setBackground(backgroundColor);
		g2.fillRect(padding + labelPadding,
				padding,
				getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);

		/** create x and y axes **/ 
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

		g2.setColor(lineColor);
		for (int i = 0; i < values[0].length - 1; i++) {
			int x1 = (int) (values[0][i] * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxScore() - values[1][i]) * yScale + padding);
			int x2 = (int) (values[0][i+1] * xScale + padding + labelPadding);
			int y2 = (int) ((getMaxScore() - values[1][i+1]) * yScale + padding);
			g2.drawLine(x1, y1, x2, y2);
		}
	}


	/** Constructors **/
	public OrbitViewer(double[][] values){
		this.values = values;
	}
	public OrbitViewer(double[][] values, int width, int height){
		this.values = values;
		this.width = width;
		this.height = height;
	}
	public OrbitViewer(){
		this.values = this.spiral_values();
	}
	public OrbitViewer(Dimension dim){
		this.values = this.spiral_values();
		this.width = (int) dim.getWidth();
		this.height = (int) dim.getHeight();
	}

	private double getMaxScore() {
		double max = Double.MIN_VALUE;
		for(int i=0; i < this.values[0].length;i++){
			max = ((this.values[0][i] > max) ? this.values[0][i] : max);
			max = ((this.values[1][i] > max) ? this.values[1][i] : max);
		}
		return max;
	}

	private double getMinScore(){
		double min = Double.MAX_VALUE;
		for(int i=0; i < this.values[0].length;i++){
			min = ((this.values[0][i] < min) ? this.values[0][i] : min);
			min = ((this.values[1][i] < min) ? this.values[1][i] : min);
		}
		return min;
	}
	
	public void setOrbitViewerStyle(){
		this.backgroundColor = Color.black;
		this.lineColor = Color.LIGHT_GRAY;
		this.pointColor = Color.lightGray;
	}

	private double[][] spiral_values(){
		double[][] spiral = new double[2][100];
		double z = 0;
		for(int i=0; i< 100; i++){
			spiral[0][i] = z * Math.cos(z) +5;
			spiral[1][i] = z * Math.sin(z) +5;
			z = z + 0.0625;
		}
		return spiral;
	}

	/** codes de test **/
	public void test(JFrame frame, Dimension dim, Point locate){
		GraphPanel test = new GraphPanel(this.spiral_values(), dim);
		//test.setLocation(locate);
		//test.setOrbitViewerStyle();
		frame.getContentPane().add(test);
		//frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void test(JFrame frame, Point locate){
		this.setOrbitViewerStyle();
		frame.getContentPane().add(this,BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void test(JFrame frame){
		this.setOrbitViewerStyle();
		frame.getContentPane().add(this,BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		OrbitViewer viewer = new OrbitViewer();
		JFrame window = new JFrame();
		window.setTitle("Nostromo");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(viewer.width * 2 , viewer.height * 2);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				viewer.test(window, new Point(viewer.padding,viewer.padding));
			}
		});
	}
}
