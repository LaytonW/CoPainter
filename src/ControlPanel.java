import javafx.scene.control.RadioButton;

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

	private PenPoint currentPen;
	private ColorChooserFrame colorChooserFrame;
	private volatile JTextField radiusText;
	private static final long serialVersionUID = 1L;
	private PaintFrame frame;
	private JButton colorButton;
	private JButton radiusUp;
	private JButton radiusDown;
	private JButton radiusButton;

	ControlPanel(PaintFrame frame) {
        currentPen = new PenPoint();
		this.frame = frame;
		this.initializeAppearance();
		this.setUpListener();

	}

	public PenPoint getPen() {
		return currentPen;
	}

	public int getRadius() {
		return currentPen.getRadius();
	}

	public void setSize(int a,int b) {
		currentPen.setSize(a,b);
	}

	public void setPenLocation(int a, int b) {
		currentPen.setLocation(a,b);
	}

	public Color getColor() {
		return currentPen.getColor();
	}

	public void setColor(Color c) {
		currentPen.setPointColor(c);
	}

	public void setPen(PenPoint pen) {
		this.currentPen=pen;
	}

	private void setUpListener() {
		colorButton.addActionListener((ActionEvent arg0) -> colorChooserFrame.setVisible(true));
		radiusUp.addMouseListener(new RadiusCustomListener("up",radiusText,currentPen));
		radiusDown.addMouseListener(new RadiusCustomListener("down",radiusText,currentPen));
		radiusText.addActionListener(new RadiusSetListener(currentPen,frame,radiusText));
		radiusButton.addActionListener(new RadiusSetListener(currentPen,frame,radiusText));
	}

	private void initializeAppearance() {
		SpringLayout controlLayout = new SpringLayout();
		this.setLayout(controlLayout);
		radiusText = new JTextField("4");
		colorChooserFrame = new ColorChooserFrame(this);
		ColorOption[] colorOptions = new ColorOption[4];
		colorOptions[0] = new ColorOption(0, 0, 0,this);
		colorOptions[1] = new ColorOption(255, 0, 0,this);
		colorOptions[2] = new ColorOption(255, 255, 255,this);
		colorOptions[3] = new ColorOption(0, 255, 0,this);
		RadiusOption[] radiusOptions = new RadiusOption[4];
		JLabel radiusLabel = new JLabel("Radius:");
		radiusUp = new JButton("+1");
		radiusDown = new JButton("-1");
		radiusButton = new JButton("Set");
		colorButton = new JButton("Custom");
		radiusText.setPreferredSize(new Dimension(70, 45));
		currentPen = new PenPoint();
		currentPen.setPointColor(0, 0, 0);
		currentPen.setRadius(4);
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
			colorOptions[i].setPreferredSize(new Dimension(45, 45));
			this.add(colorOptions[i]);
			controlLayout.putConstraint(SpringLayout.NORTH, colorOptions[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, colorOptions[i], 5,
					i == 0 ? SpringLayout.WEST : SpringLayout.EAST, i == 0 ? this : colorOptions[i - 1]);
		}
		controlLayout.putConstraint(SpringLayout.NORTH, colorButton, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, colorButton, 10, SpringLayout.EAST, colorOptions[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusLabel, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusLabel, 30, SpringLayout.EAST, colorButton);
		for (int i = 0; i < 4; i++) {
			radiusOptions[i] = new RadiusOption(4 * (i + 1),currentPen,radiusText);
			radiusOptions[i].setPreferredSize(new Dimension(45, 45));
			this.add(radiusOptions[i]);
			controlLayout.putConstraint(SpringLayout.NORTH, radiusOptions[i], 0, SpringLayout.NORTH, this);
			controlLayout.putConstraint(SpringLayout.WEST, radiusOptions[i], 5, SpringLayout.EAST,
					i == 0 ? radiusLabel : radiusOptions[i - 1]);
		}
		controlLayout.putConstraint(SpringLayout.NORTH, radiusText, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusText, 10, SpringLayout.EAST, radiusOptions[3]);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusButton, 0, SpringLayout.NORTH, radiusText);
		controlLayout.putConstraint(SpringLayout.WEST, radiusButton, 10, SpringLayout.EAST, radiusText);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusUp, 0, SpringLayout.NORTH, this);
		controlLayout.putConstraint(SpringLayout.WEST, radiusUp, 5, SpringLayout.EAST, radiusButton);
		controlLayout.putConstraint(SpringLayout.NORTH, radiusDown, 0, SpringLayout.SOUTH, radiusUp);
		controlLayout.putConstraint(SpringLayout.WEST, radiusDown, 5, SpringLayout.EAST, radiusButton);

	}
}

class RadiusOption extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int radius;
	private PenPoint currentPen;
	private JTextField radiusText;

	RadiusOption(int r,PenPoint currentPen,JTextField radiusText) {
		this.currentPen=currentPen;
		this.radiusText=radiusText;
		this.radius=r;
	}


	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 45, 45);
		g.setColor(Color.BLACK);
		g.fillOval(45 / 2 - radius, 45 / 2 - radius, radius * 2, radius * 2);
		this.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		currentPen.setRadius(radius);
		radiusText.setText(Integer.toString(radius));
	}
}

class ColorOption extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private int R;
	private int G;
	private int B;
	private ControlPanel controlPanel;


	public void paintComponent(Graphics g) {
		g.setColor(new Color(R, G, B));
		g.fillRect(0, 0, 45, 45);
		this.addActionListener(this);
	}

	ColorOption(int red, int green, int blue,ControlPanel controlPanel) {
		R = red;
		G = green;
		B = blue;
		this.controlPanel=controlPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controlPanel.setColor(new Color(R,G,B));
		controlPanel.getPen().repaint();
	}
}

class RadiusSetListener implements ActionListener {

	private PenPoint currentPen;
	private PaintFrame frame;
	private JTextField radiusText;

	RadiusSetListener(PenPoint pen,PaintFrame panel,JTextField radiusText) {
		this.currentPen=pen;
		this.frame=panel;
		this.radiusText=radiusText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int r = Integer.parseInt(radiusText.getText());
			if (r > 0 && r < 350) {
				currentPen.setRadius(r);
				if(frame.getMousePosition() != null)
					currentPen.setLocation(frame.getMousePosition().x-currentPen.getRadius(),frame.getMousePosition().y-currentPen.getRadius());
			} else
				JOptionPane.showMessageDialog(null,
						"Invalid Input. radius must in the range of 1-349");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Invalid Input");
		} finally {
		    if (radiusText==null) {
		        System.out.println("radiusText is null");
            }
            System.out.println("*"+String.valueOf(currentPen.getRadius())+"*");
			radiusText.setText(String.valueOf(currentPen.getRadius()));
			frame.repaintPanel();
		}
	}
}

class RadiusCustomListener implements MouseListener {

	private String change;
	private volatile boolean pressed;
	private volatile boolean running;
	private PenPoint currentPen;
	private JTextField radiusText;


	RadiusCustomListener(String c,JTextField radiusText, PenPoint pen) {
		change=c;
		pressed = false;
		running = false;
		currentPen=pen;
		this.radiusText=radiusText;
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
								currentPen.setRadius(r);
								radiusText.setText(Integer.toString(r));
							}
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null,
									"Invalid Input. radius must in the range of 1-349");
							ex.printStackTrace();
							radiusText.setText(String.valueOf(currentPen.getRadius()));
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