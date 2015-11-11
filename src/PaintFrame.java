import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PaintFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PaintPanel paintPanel;
	public static JMenuBar menuBar;
	PaintFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.setTitle("Collaborative Painter");
		this.setResizable(false);
		this.setLayout(null);
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Control");
		JMenuItem clear = new JMenuItem("Clear");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem exit = new JMenuItem("Exit");
		ControlPanel controlPanel = new ControlPanel();
		paintPanel = new PaintPanel();
		menu.add(clear);
		menu.add(save);
		menu.add(load);
		menu.add(exit);
		menuBar.add(menu);
		this.getContentPane().add(menuBar);
		menuBar.setLocation(0, 0);
		this.getContentPane().add(paintPanel);
		controlPanel.setMaximumSize(new Dimension(700, 100));
		this.getContentPane().add(controlPanel);
		menuBar.setSize(700, 25);
		paintPanel.setBounds(0,25,700, 605);
		paintPanel.setBackground(Color.WHITE);
		controlPanel.setBounds(0, 630,700,45);
		this.setVisible(true);
	}
}
