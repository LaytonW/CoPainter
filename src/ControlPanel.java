import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	public static PenPoint current;
	private static final long serialVersionUID = 1L;

	ControlPanel() {
		SpringLayout controlLayout = new SpringLayout();
		this.setLayout(controlLayout);
		ColorOption[] colorOption = new ColorOption[4];
		colorOption[0] = new ColorOption(0, 0, 0);
		colorOption[1] = new ColorOption(255, 0, 0);
		colorOption[2] = new ColorOption(255, 255, 255);
		colorOption[3]=new ColorOption(0,255,0);
		RadiusOption[] radiusOption = new RadiusOption[4];
		JLabel radiusLabel = new JLabel("Radius:");
		JTextField radiusText = new JTextField();
		JButton radiusButton = new JButton("Set");
		JButton colorButton = new JButton("Custom");
		radiusText.setPreferredSize(new Dimension(70, 45));
		current = new PenPoint();
		current.setPointColor(0, 0, 0);
		current.setRadius(4);
		radiusLabel.setPreferredSize(new Dimension(80, 45));
		radiusButton.setPreferredSize(new Dimension(80, 45));
		colorButton.setPreferredSize(new Dimension(90, 45));
		this.add(colorButton);
		this.add(radiusLabel);
		this.add(radiusText);
		this.add(radiusButton);
		this.setSize(700, 45);
		radiusText.setFont(new Font(null, Font.PLAIN, 40));
		for (int i = 0; i < 4; i++) {
			colorOption[i].setPreferredSize(new Dimension(45, 45));
			this.add(colorOption[i]);
			//colorOption[i].setBounds(45 * i, 0, 45, 45);
			controlLayout.putConstraint(SpringLayout.NORTH, colorOption[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, colorOption[i], 5,
										i==0?SpringLayout.WEST:SpringLayout.EAST,
										i==0?this:colorOption[i-1]);
		}
		controlLayout.putConstraint(SpringLayout.NORTH, colorButton, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, colorButton, 10, SpringLayout.EAST, colorOption[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusLabel, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusLabel, 30, SpringLayout.EAST, colorButton);
		for (int i = 0; i < 4; i++) {
			radiusOption[i] = new RadiusOption(4 * (i + 1));
			radiusOption[i].setPreferredSize(new Dimension(45, 45));
			this.add(radiusOption[i]);
			//radiusOption[i].setBounds(300 + 45 * i, 0, 45, 45);
			controlLayout.putConstraint(SpringLayout.NORTH, radiusOption[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, radiusOption[i], 5, SpringLayout.EAST,
										i==0?radiusLabel:radiusOption[i-1]);
		}

		//radiusLabel.setBounds(490, 0, 50, 45);
		//radiusText.setBounds(540, 0, 70, 45);
		//radiusButton.setBounds(610, 0, 90, 45);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusText, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusText, 10, SpringLayout.EAST, radiusOption[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusButton, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusButton, 10, SpringLayout.EAST, radiusText);
		//colorButton.setBounds(180, 0, 90, 45);
		current.setBounds(0, 0, 700, 700);
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ColorChooserFrame();
			}
		});
		class RadiusCustomListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					int r = Integer.parseInt(radiusText.getText());
					if (r > 0 && r<350) {
						current.setRadius(r);
						current.repaint();
						radiusText.setText(null);
					} else
						JOptionPane.showMessageDialog(null, "Invalid Input. radius must in the range of 0-350");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
		}
		RadiusCustomListener RCL=new RadiusCustomListener();
		radiusButton.addActionListener(RCL);
		radiusText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}