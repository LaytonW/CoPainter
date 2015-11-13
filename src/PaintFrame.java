import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SpringLayout;

public class PaintFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PaintPanel paintPanel;
	public static JMenuBar menuBar;
	public static String mode;
	PaintFrame(String m) {
		mode=m;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 700);
		this.setTitle("Collaborative Painter");
		this.setResizable(false);
		SpringLayout mainLayout = new SpringLayout();
		this.getContentPane().setLayout(mainLayout);
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Control");
		JMenuItem clear = new JMenuItem("Clear");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem exit = new JMenuItem("Exit");
		ControlPanel controlPanel = new ControlPanel();
		paintPanel = new PaintPanel();
		if (mode=="Server") {
			menu.add(clear);
			menu.add(load);
		}
		menu.add(save);
		menu.add(exit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		controlPanel.setPreferredSize(new Dimension(800, 100));
		paintPanel.setPreferredSize(new Dimension(800, 600));
		paintPanel.setBackground(Color.WHITE);
		this.getContentPane().add(paintPanel);
		this.getContentPane().add(controlPanel);
		mainLayout.putConstraint(SpringLayout.NORTH, paintPanel, 0, SpringLayout.NORTH, this);
		mainLayout.putConstraint(SpringLayout.WEST, paintPanel, 0, SpringLayout.WEST, this);
		mainLayout.putConstraint(SpringLayout.NORTH, controlPanel, 0, SpringLayout.SOUTH, paintPanel);
		mainLayout.putConstraint(SpringLayout.WEST, controlPanel, 0, SpringLayout.WEST, this);
		clear.addActionListener(new MenuListener("clear"));
		exit.addActionListener(new MenuListener("exit"));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
