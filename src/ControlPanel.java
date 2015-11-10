import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class ControlPanel extends JPanel{
	
	/**
	 * 
	 */
	public Point current;
	private static final long serialVersionUID = 1L;
	ControlPanel() {
		this.setSize(700,100);
		current=new Point();
		this.add(current);
		current.setSize(50,50);
		current.setColor(0, 0, 255);
		current.setRadius(50);
	}
}
class Point extends JPanel{
	private int radius;
	private int R;
	private int G;
	private int B;
	private int x;
	private int y;
	public void paintComponent(Graphics g) {
		g.setColor(new Color(R,G,B));
		g.fillOval(0,0, radius, radius);
	}
	public void setColor(int red, int green, int blue) {
		R=red;
		G=green;
		B=blue;
	}
	public void setRadius(int r) {
		radius=r;
	}
	public void setLocation (int dx, int dy) {
		dx=x;
		dy=y;
	}
}
class colors extends JPanel {
	private int R;
	private int G;
	private int B;
	public void paintComponent(Graphics g) {
		g.setColor(new Color(R,G,B));
		g.fillRect(0, 0, 50, 50);
	}
	colors(int red, int green, int blue) {
		R=red;
		G=green;
		B=blue;
	}
}