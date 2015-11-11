import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Point extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int radius;
	private int R;
	private int G;
	private int B;
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 700, 700);
		g.setColor(new Color(R, G, B));
		g.fillOval(0, 0, radius, radius);
//		setSize(2*radius,2*radius);
	}

	public void setPointColor(int red, int green, int blue) {
		R = red;
		G = green;
		B = blue;
	}

	public void setRadius(int r) {
		radius = r;
	}
}
