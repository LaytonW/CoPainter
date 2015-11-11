import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ColorCustomListener implements ActionListener {
	public static int red;
	public static int green;
	public static int blue;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JFrame popup = new JFrame();
		popup.setTitle("Custom Color");
		popup.setSize(170, 120);
		popup.setResizable(false);
		popup.setLayout(null);
		JLabel Red = new JLabel("R:");
		JLabel Green = new JLabel("G:");
		JLabel Blue = new JLabel("B:");
		JTextField R = new JTextField();
		JTextField G = new JTextField();
		JTextField B = new JTextField();
		JButton OK = new JButton("OK");
		popup.add(Red);
		popup.add(R);
		popup.add(Green);
		popup.add(G);
		popup.add(Blue);
		popup.add(B);
		popup.add(OK);
		Red.setBounds(0, 0, 50, 20);
		R.setBounds(30, 0, 50, 20);
		Green.setBounds(0, 20, 30, 20);
		G.setBounds(30, 20, 50, 20);
		Blue.setBounds(0, 40, 30, 20);
		B.setBounds(30, 40, 50, 20);
		OK.setBounds(80, 0, 60, 60);
		OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					red = Integer.parseInt(R.getText());
					green = Integer.parseInt(G.getText());
					blue = Integer.parseInt(B.getText());
					if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
						popup.setVisible(false);
						popup.dispose();
						ControlPanel.current.setPointColor(red, green, blue);
						ControlPanel.current.repaint();
					} else
						JOptionPane.showMessageDialog(null, "Invalid Input");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
		});
		popup.setVisible(true);
	}
}
