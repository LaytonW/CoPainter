import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaintFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PaintPanel paintPanel;
	public static JMenuBar menuBar;
	private int port;
	private String ip;
	PaintFrame(NetworkManager n,int p,String h) {
		port=p;
		ip=h;
		if(ip==null)
			ip="localhost";
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
		JMenu helpMenu = new JMenu("Help");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem about=new JMenuItem("About");
		ControlPanel controlPanel = new ControlPanel();
		paintPanel = new PaintPanel(n);
		if (n instanceof ServerManager) {
			menu.add(clear);
			menu.addSeparator();
			menu.add(load);
		}
		menu.add(save);
		menu.add(exit);
		helpMenu.add(help);
		helpMenu.add(about);
		menuBar.add(menu);
		menuBar.add(helpMenu);
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
							saverObjectStream.writeObject(PaintPanel.buffer);
							saverObjectStream.writeObject(ControlPanel.current);
							saverObjectStream.close();
							done = true;
						} catch (Exception fe) {
							JOptionPane.showMessageDialog(rootPane, "Error saving file!\n"
									+ fe.toString(), "Save failed", JOptionPane.ERROR_MESSAGE);
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
							if (!(obj1 instanceof CopyOnWriteArrayList<?> && obj2 instanceof PenPoint)) {
								JOptionPane.showMessageDialog(rootPane, "Resolving file "
										+ loadFile.getName() + " failed!",
										"File broken", JOptionPane.ERROR_MESSAGE);
								break;
							}
							PaintPanel.buffer = (CopyOnWriteArrayList<Path>) obj1;
							paintPanel.loadToNetwork();
							ControlPanel.current = (PenPoint) obj2;
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
		about.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String IP="";
				try{
					IP="Local IP: "+String.valueOf(InetAddress.getLocalHost());
				}catch(Exception ex){
				}
				String status;
				if(n instanceof ServerManager)
					status="Server";
				else
					status="Client";
				JOptionPane.showMessageDialog(null,"Status: "+status+"\n"+"Server IP: "+ip+"\n"+IP+"\n"+"Port: "+Integer.toString(port));
			}
			
		});
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
