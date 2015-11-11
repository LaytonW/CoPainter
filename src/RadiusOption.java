import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RadiusOption extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radius;

	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 45, 45);
		g.setColor(Color.BLACK);
		g.fillOval(32 - radius, 32 - radius, radius, radius);
		this.addActionListener(this);
	}

	RadiusOption(int r) {
		radius = r;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ControlPanel.current.setRadius(radius);
		ControlPanel.current.repaint();
	}

}
