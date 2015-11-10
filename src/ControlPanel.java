import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControlPanel extends JPanel{
	
	/**
	 * 
	 */
	public static Point current;
	private static final long serialVersionUID = 1L;
	ControlPanel() {
		this.setLayout(null);
		ColorOption black=new ColorOption(0,0,0);
		ColorOption red=new ColorOption(255,0,0);
		ColorOption white=new ColorOption(255,255,255);
		RadiusOption[] radiusOption=new RadiusOption[4];
		JLabel Radius=new JLabel("Radius:");
		JTextField radius=new JTextField();
		JButton changeRadius=new JButton("Set");
		JButton custom=new JButton("Custom");
		current=new Point();
		current.setPointColor(255, 0, 255);
		current.setRadius(20);
		this.add(current);
		this.add(black);
		this.add(red);
		this.add(white);
		this.add(custom);
		this.add(Radius);
		this.add(radius);
		this.add(changeRadius);
		this.setSize(700,45);
		current.setSize(45, 45);
		black.setSize(45, 45);
		red.setSize(45, 45);
		white.setSize(45, 45);
		custom.setSize(90, 45);
		Radius.setSize(50, 45);
		radius.setFont(new Font(null,Font.PLAIN,40));
		radius.setSize(70, 45);
		changeRadius.setSize(90, 45);
		current.setLocation(0,0);
		black.setLocation(45, 0);
		red.setLocation(135, 0);
		white.setLocation(90, 0);
		for(int i=0;i<4;i++)
		{
			radiusOption[i]=new RadiusOption(5*(i+1));
			this.add(radiusOption[i]);
			radiusOption[i].setSize(45, 45);
			radiusOption[i].setLocation(300+45*i, 0);
		}
		Radius.setLocation(490, 0);
		radius.setLocation(540, 0);
		changeRadius.setLocation(610, 0);
		custom.setLocation(180, 0);
		custom.addActionListener(new ColorCustomListener());
		class RadiusCustomListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					int r=Integer.parseInt(radius.getText());
					if(r>0) {
						current.setRadius(r);
						current.repaint();
					}
					else
						JOptionPane.showMessageDialog(null, "Invalid Input");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
			
		}
		changeRadius.addActionListener(new RadiusCustomListener ());
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
		if(radius<32) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 45, 45);
			g.setColor(new Color(R,G,B));
			g.fillOval(32-radius,32-radius, radius, radius);
		}
		else  {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 45, 45);
			g.setColor(Color.BLACK);
			g.drawString("N/A", 0, 22);
		}
	}
	public void setPointColor(int red, int green, int blue) {
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