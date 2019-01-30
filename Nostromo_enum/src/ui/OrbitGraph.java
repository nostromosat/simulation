package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class OrbitGraph extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int marge = 20;
	private int width = 800;
	private int height = 500;
	private int xPos = -75;
	private int yPos = -250;
	private int xScale = 5;
	private int yScale = 3;
	private double max_spiral = Double.MIN_VALUE;

	public OrbitGraph(){
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1000,1000));
		this.add(new DrawStuff(), BorderLayout.CENTER);
		revalidate();
		repaint();
		this.setVisible(true); //probably not necessary

	}

	public OrbitGraph(Dimension dim){
		setLayout(new BorderLayout());
		this.setPreferredSize(dim);
		this.add(new DrawStuff(), BorderLayout.CENTER);
		revalidate();
		repaint();
		this.setVisible(true); //probably not necessary

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

	public class DrawStuff extends JComponent{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1;



		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);

			Graphics2D graph2 = (Graphics2D) g;

			graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			BufferedImage sky = null;
			try{
				sky = ImageIO.read(new File("src/ui/Sky.png"));
				graph2.drawImage(sky,marge*3,0,width,height,this);
			}
			catch (IOException e) {
				System.out.println("not the good directory");
			}

			double[][] orbit = spiral_values();
			for(int i=0;i<orbit[0].length;i++){

				max_spiral = ((orbit[0][i] > max_spiral) ? orbit[0][i] : max_spiral); 
				max_spiral = ((orbit[1][i] > max_spiral) ? orbit[1][i] : max_spiral);
			};

			for(int i=0;i<orbit[0].length;i++){
				orbit[0][i] = orbit[0][i] / (max_spiral+marge*2) * Math.min(width, height) * xScale - marge;
				orbit[1][i] = orbit[1][i] / (max_spiral+marge*2) * Math.min(width, height) * yScale - marge;
			}

			int xOrigin = (int) (xPos+width)/2;
			int yOrigin = (int) (yPos+height)/2;

			graph2.setColor(Color.white);
			Stroke oldStroke = graph2.getStroke();
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
			graph2.setStroke(dashed);

			for(int i=0;i<orbit[0].length-1;i++){
				int x0 = (int)orbit[0][i] + xOrigin/2;
				int y0 = (int)orbit[1][i] + yOrigin;
				int x1 = (int)orbit[0][i+1] + xOrigin/2;
				int y1 = (int)orbit[1][i+1] + yOrigin;
				graph2.drawLine(x0, y0, x1, y1);
			}
			graph2.setStroke(oldStroke);

			for(int i=0;i<orbit[0].length;i++){

			}

			BufferedImage earth = null;
			BufferedImage nostromo = null;
			BufferedImage asteroid = null;
			try {
				earth = ImageIO.read(new File("src/ui/Earth.png"));
				nostromo = ImageIO.read(new File("src/ui/Nostromo.png"));
				asteroid = ImageIO.read(new File("src/ui/Asteroid.png"));

				int i = orbit[0].length - orbit[0].length / 5;
				int last = orbit[0].length-1;

				graph2.drawImage(earth, (int)orbit[0][0]+xOrigin/2-marge*2, (int)orbit[1][0]+yOrigin-marge, marge*3, marge*3, this);
				graph2.drawImage(nostromo, (int)orbit[0][i]+xOrigin/2-marge, (int)orbit[1][i]+yOrigin-marge, marge*2, marge*2, this);
				graph2.drawImage(asteroid, (int)orbit[0][last]+xOrigin/2-marge, (int)orbit[1][last]+yOrigin-marge, marge*2, marge*2, this);

			} catch (IOException e) {
				System.out.println("not the good directory");
			}
		}
	}
}