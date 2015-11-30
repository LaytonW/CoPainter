import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

public class PenPoint extends JComponent {

	private static final long serialVersionUID = 1L;
	private int radius;
	private Color color;

	PenPoint() {
		setOpaque(true);
	}

	public void paintComponent(Graphics g) {
		setSize(radius*2 + 1,radius*2 + 1);
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
		setSize(radius * 2 + 1, radius * 2 + 1);
	}
	public Color getColor() {
		return color;
	}
	public int getRadius() {
		return radius;
	}
}
