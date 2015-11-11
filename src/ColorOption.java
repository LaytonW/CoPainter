import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ColorOption extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int R;
	private int G;
	private int B;

	public void paintComponent(Graphics g) {
		g.setColor(new Color(R, G, B));
		g.fillRect(0, 0, 45, 45);
		this.addActionListener(this);
	}

	ColorOption(int red, int green, int blue) {
		R = red;
		G = green;
		B = blue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ControlPanel.current.setPointColor(R, G, B);
		ControlPanel.current.repaint();
	}
}
