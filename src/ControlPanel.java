import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		JTextField radiusText = new JTextField("4");
		class RadiusOption extends JButton implements ActionListener {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private int radius;

			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 45, 45);
				g.setColor(Color.BLACK);
				g.fillOval(45 / 2 - radius, 45 / 2 - radius, radius * 2, radius * 2);
				this.addActionListener(this);
			}

			RadiusOption(int r) {
				radius = r;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				current.setRadius(radius);
				radiusText.setText(Integer.toString(radius));
			}
		}
		class ColorOption extends JButton implements ActionListener {
			private static final long serialVersionUID = 1L;
			private int R;
			private int G;
			private int B;

			public void paintComponent(Graphics g) {
				g.setColor(new Color(R, G, B));
				g.fillRect(0, 0, 45, 45);
				radiusText.setText(String.valueOf(current.getRadius()));
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
				current.setPointColor(R, G, B);
				current.repaint();
			}
		}
		ColorOption[] colorOption = new ColorOption[4];
		colorOption[0] = new ColorOption(0, 0, 0);
		colorOption[1] = new ColorOption(255, 0, 0);
		colorOption[2] = new ColorOption(255, 255, 255);
		colorOption[3] = new ColorOption(0, 255, 0);
		RadiusOption[] radiusOption = new RadiusOption[4];
		JLabel radiusLabel = new JLabel("Radius:");
		JButton radiusUp = new JButton("+1");
		JButton radiusDown = new JButton("-1");
		JButton colorButton = new JButton("Custom");
		radiusText.setPreferredSize(new Dimension(70, 45));
		current = new PenPoint();
		current.setPointColor(0, 0, 0);
		current.setRadius(4);
		radiusLabel.setPreferredSize(new Dimension(80, 45));
		radiusUp.setPreferredSize(new Dimension(80, 45/2));
		radiusDown.setPreferredSize(new Dimension(80, 45/2));
		colorButton.setPreferredSize(new Dimension(90, 45));
		this.add(colorButton);
		this.add(radiusLabel);
		this.add(radiusText);
		this.add(radiusUp);
		this.add(radiusDown);
		this.setSize(700, 45);
		radiusText.setFont(new Font(null, Font.PLAIN, 40));
		for (int i = 0; i < 4; i++) {
			colorOption[i].setPreferredSize(new Dimension(45, 45));
			this.add(colorOption[i]);
			controlLayout.putConstraint(SpringLayout.NORTH, colorOption[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, colorOption[i], 5,
					i == 0 ? SpringLayout.WEST : SpringLayout.EAST, i == 0 ? this : colorOption[i - 1]);
		}
		controlLayout.putConstraint(SpringLayout.NORTH, colorButton, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, colorButton, 10, SpringLayout.EAST, colorOption[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusLabel, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusLabel, 30, SpringLayout.EAST, colorButton);
		for (int i = 0; i < 4; i++) {
			radiusOption[i] = new RadiusOption(4 * (i + 1));
			radiusOption[i].setPreferredSize(new Dimension(45, 45));
			this.add(radiusOption[i]);
			controlLayout.putConstraint(SpringLayout.NORTH, radiusOption[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, radiusOption[i], 5, SpringLayout.EAST,
					i == 0 ? radiusLabel : radiusOption[i - 1]);
		}
		controlLayout.putConstraint(SpringLayout.NORTH, radiusText, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusText, 10, SpringLayout.EAST, radiusOption[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusUp, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusUp, 10, SpringLayout.EAST, radiusText);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusDown, 20, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusDown, 10, SpringLayout.EAST, radiusText);
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ColorChooserFrame();
			}
		});
		class RadiusCustomListener implements ActionListener {
			public String change;
			RadiusCustomListener(String c) {
				change=c;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					int r = Integer.parseInt(radiusText.getText());
					if (r > 0 && r < 350) {
						if (change=="up" && r!=349) {
							r=r+1;
						}
						else if (change=="down"&&r!=1) {
							r=r-1;
						}
						current.setRadius(r);
						radiusText.setText(Integer.toString(r));
						radiusText.selectAll();
						radiusText.repaint();
						current.repaint();
						PaintFrame.paintPanel.repaint();
						repaint();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input. radius must in the range of 1-349");
				}
			}
		}
		radiusUp.addActionListener(new RadiusCustomListener("up"));
		radiusDown.addActionListener(new RadiusCustomListener("down"));
		radiusText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					int r = Integer.parseInt(radiusText.getText());
					if (r > 0 && r < 350) {
						current.setRadius(r);
						current.repaint();
						PaintFrame.paintPanel.repaint();
						repaint();
					} else
						JOptionPane.showMessageDialog(null, "Invalid Input. radius must in the range of 1-349");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
		});
	}
}