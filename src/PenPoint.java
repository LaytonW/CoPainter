import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PenPoint extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int radius;
	private Color color;
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(0, 0, radius*2, radius*2);
		g.setColor(color);
		g.fillOval(0, 0, radius*2, radius*2);
	}

	public void setPointColor(int red, int green, int blue) {
		color=new Color(red,green,blue);
	}
	public void setPointColor (Color c) {
		color=c;
	}
	public void setRadius(int r) {
		radius = r;
	}
	public Color getColor() {
		return color;
	}
}
