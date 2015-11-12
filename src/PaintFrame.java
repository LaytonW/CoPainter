import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaintFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PaintPanel paintPanel;
	public static JMenuBar menuBar;
	PaintFrame() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		menu.add(clear);
		menu.add(save);
		menu.add(load);
		menu.add(exit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		//menuBar.setLocation(0, 0);
		controlPanel.setPreferredSize(new Dimension(800, 100));
		//menuBar.setSize(700, 25);
		//paintPanel.setBounds(0,25,700, 605);
		paintPanel.setPreferredSize(new Dimension(800, 600));
		paintPanel.setBackground(Color.WHITE);
		//controlPanel.setBounds(0, 630,700,45);
		this.getContentPane().add(paintPanel);
		this.getContentPane().add(controlPanel);
		mainLayout.putConstraint(SpringLayout.NORTH, paintPanel, 0, SpringLayout.NORTH, this);
		mainLayout.putConstraint(SpringLayout.WEST, paintPanel, 0, SpringLayout.WEST, this);
		mainLayout.putConstraint(SpringLayout.NORTH, controlPanel, 0, SpringLayout.SOUTH, paintPanel);
		mainLayout.putConstraint(SpringLayout.WEST, controlPanel, 0, SpringLayout.WEST, this);
		//this.pack();
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintPanel.clear();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileSaver = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						".pb Painting Board File", "pb");
				fileSaver.setFileFilter(filter);
				fileSaver.setSelectedFile(new File("Untitled.pb"));
				boolean done = false;
				do {
					if (fileSaver.showSaveDialog(rootPane) == JFileChooser.APPROVE_OPTION) {
						try {
							File saveFile = fileSaver.getSelectedFile();
							if (saveFile.exists()) {
								if (JOptionPane.showConfirmDialog(rootPane, "File "
										+ saveFile.getName() + " already exists.\n"
										+ "Do you want to overwrite it?", "Overwrite?",
										JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
									continue;
							}
							FileOutputStream saverFileStream = new FileOutputStream(saveFile);
							ObjectOutputStream saverObjectStream = new ObjectOutputStream(saverFileStream);
							saverObjectStream.writeObject(paintPanel.paths);
							saverObjectStream.writeObject(ControlPanel.current);
							saverObjectStream.close();
							done = true;
						} catch (Exception fe) {
							JOptionPane.showMessageDialog(rootPane, fe.toString());
						}
					}
					done = true;
				} while (!done);
			}
		});
		load.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileLoader = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						".pb Painting Board File", "pb");
				fileLoader.setFileFilter(filter);
				boolean done = false;
				do {
					if (fileLoader.showOpenDialog(rootPane) == JFileChooser.APPROVE_OPTION) {
						try {
							File loadFile = fileLoader.getSelectedFile();
							if (!loadFile.exists()) {
								JOptionPane.showMessageDialog(rootPane, "File "
										+ loadFile.getName() + " does not exist!",
										"File does not exist", JOptionPane.ERROR_MESSAGE);
								continue;
							}
							FileInputStream loadFileStream = new FileInputStream(loadFile);
							ObjectInputStream loadObjectStream = new ObjectInputStream(loadFileStream);
							Object obj1 = loadObjectStream.readObject();
							Object obj2 = loadObjectStream.readObject();
							loadObjectStream.close();
							if (!(obj1 instanceof ArrayList<?> && obj2 instanceof PenPoint)) {
								JOptionPane.showMessageDialog(rootPane, "Resolving file "
										+ loadFile.getName() + " failed!",
										"File broken", JOptionPane.ERROR_MESSAGE);
								break;
							}
							paintPanel.paths = (ArrayList<Path>) obj1;
							ControlPanel.current = (PenPoint) obj2;
							//paintPanel.revalidate();
							done = true;
						} catch (java.io.StreamCorruptedException streamEx) {
							JOptionPane.showMessageDialog(rootPane, "Resolving file failed!\n"
									+ "Exception message:\n" + streamEx.toString(),
									"File broken", JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException classEx) {
							JOptionPane.showMessageDialog(rootPane, "Resolving file failed!\n"
									+ "Exception message:\n" + classEx.toString(),
									"File broken", JOptionPane.ERROR_MESSAGE);
						} catch (java.io.IOException ioEx) {
							JOptionPane.showMessageDialog(rootPane, "Input/Output exception caught!\n"
									+ "Exception message:\n" + ioEx.toString(),
									"Exception", JOptionPane.ERROR_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(rootPane, "Unknown exception caught!\n"
									+ "Exception message:\n" + ex.toString(),
									"Exception", JOptionPane.ERROR_MESSAGE);
						}
					}
					done = true;
				} while (!done);
			}
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
