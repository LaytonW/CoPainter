import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class ControlPanel extends JPanel{

	public static PenPoint current;
	private ColorChooserFrame colorChooserFrame;
	private volatile JTextField radiusText;
	private static final long serialVersionUID = 1L;
	ControlPanel() {
		SpringLayout controlLayout = new SpringLayout();
		this.setLayout(controlLayout);
		radiusText = new JTextField("4");
		colorChooserFrame = new ColorChooserFrame();
		class RadiusOption extends JButton implements ActionListener {

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
		JButton radiusButton = new JButton("Set");
		JButton colorButton = new JButton("Custom");
		radiusText.setPreferredSize(new Dimension(70, 45));
		current = new PenPoint();
		current.setPointColor(0, 0, 0);
		current.setRadius(4);
		radiusLabel.setPreferredSize(new Dimension(55, 45));
		radiusUp.setPreferredSize(new Dimension(60, 45/2));
		radiusDown.setPreferredSize(new Dimension(60, 45/2));
		radiusButton.setPreferredSize(new Dimension(60, 45));
		colorButton.setPreferredSize(new Dimension(90, 45));
		this.add(colorButton);
		this.add(radiusLabel);
		this.add(radiusText);
		this.add(radiusButton);
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
		controlLayout.putConstraint(SpringLayout.NORTH, radiusButton, 0, SpringLayout.NORTH, radiusText);
		controlLayout.putConstraint(SpringLayout.WEST, radiusButton, 10, SpringLayout.EAST, radiusText);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusUp, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusUp, 5, SpringLayout.EAST, radiusButton);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusDown, 0, SpringLayout.SOUTH, radiusUp);
		controlLayout.putConstraint(SpringLayout.WEST, radiusDown, 5, SpringLayout.EAST, radiusButton);
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				colorChooserFrame.setVisible(true);
			}
		});
		class RadiusCustomListener implements MouseListener {
			
			private String change;
			private volatile boolean pressed;
			private volatile boolean running;
			
			RadiusCustomListener(String c) {
				change=c;
				pressed = false;
				running = false;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				pressed = true;
				startChangingThread();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				pressed = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			private synchronized boolean check() {
				if(running) return false;
				running = true;
				return true;
			}
			
			private void startChangingThread() {
				if (check()) {
					new Thread() {
						public void run() {
							do {
								try {
									int r = Integer.parseInt(radiusText.getText());
									if (r > 0 && r < 350) {
										if (change=="up" && r!=349) {
											++r;
											Thread.sleep(150);
										}
										else if (change=="down"&&r!=1) {
											--r;
											Thread.sleep(150);
										}
										current.setRadius(r);
										radiusText.setText(Integer.toString(r));
									}
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(null,
											"Invalid Input. radius must in the range of 1-349");
									ex.printStackTrace();
									radiusText.setText(String.valueOf(current.getRadius()));
									pressed = false;
									running = false;
									return;
								}
							} while (pressed);
							running = false;
						}
					}.start();
				}
			}
		}
		class RadiusSetListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int r = Integer.parseInt(radiusText.getText());
					if (r > 0 && r < 350) {
						current.setRadius(r);
						current.repaint();
						PaintFrame.paintPanel.repaint();
						repaint();
					} else
						JOptionPane.showMessageDialog(null,
								"Invalid Input. radius must in the range of 1-349");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				} finally {
					radiusText.setText(String.valueOf(current.getRadius()));
				}
			}
		}
		radiusUp.addMouseListener(new RadiusCustomListener("up"));
		radiusDown.addMouseListener(new RadiusCustomListener("down"));
		radiusText.addActionListener(new RadiusSetListener());
		radiusButton.addActionListener(new RadiusSetListener());
	}
}