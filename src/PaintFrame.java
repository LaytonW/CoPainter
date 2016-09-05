import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Position;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaintFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private PaintPanel paintPanel;
	private JMenuBar menuBar;
	private JMenuItem clear;
	private JMenuItem exit;
	private JMenuItem save;
	private JMenuItem load;
	private JMenuItem help;
	private JMenuItem about;
	private NetworkManager manager;
	private FileManager fileManager;
    private ControlPanel controlPanel;

	PaintFrame(NetworkManager manager) {
		this.manager=manager;
        this.manager.setFrame(this);
		this.fileManager = new FileManager(this);
		this.initializeAppearance();
        this.setUpListener();

	}


	private void setUpListener() {
        clear.addActionListener((ActionEvent e) -> paintPanel.clear());
        exit.addActionListener((ActionEvent e) ->System.exit(0));
        save.addActionListener((ActionEvent ae) -> fileManager.save());
        load.addActionListener((ActionEvent ae) -> fileManager.load());
        help.addActionListener((ActionEvent ae) -> new HelpDialog().setVisible(true));
        about.addActionListener((ActionEvent e) -> {
            String IP="";
            try{
                IP="Local IP: "+String.valueOf(InetAddress.getLocalHost());
            }catch(Exception ex){
                ;
            }
            if(manager instanceof ServerManager)
                JOptionPane.showMessageDialog(null,"Status: Server\n"+"Server IP: localhost\n"+IP+"\n"+"Port: "+Integer.toString(manager.getPort())+"\nClient Number: "+((ServerManager)manager).getClientNumber());
            else
                JOptionPane.showMessageDialog(null,"Status: Client\n"+"Server IP: "+((ClientManager)manager).getHost()+"\n"+IP+"\n"+"Port: "+Integer.toString(manager.getPort()));
        });
    }

    public PenPoint getPen() {
        return controlPanel.getPen();
    }

    public Point getMousePosition() {
        return paintPanel.getMousePosition();
    }

    public void setPen(PenPoint pen) {
        controlPanel.setPen(pen);
    }

	private void initializeAppearance() {
		this.setDefaultCloseOperation(0);
		this.setSize(800, 700);
		this.setTitle("Collaborative Painter");
		this.setResizable(false);
		SpringLayout mainLayout = new SpringLayout();
		this.getContentPane().setLayout(mainLayout);
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Control");
		clear = new JMenuItem("Clear");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		exit = new JMenuItem("Exit");
		JMenu helpMenu = new JMenu("Help");
		help = new JMenuItem("Help");
		about=new JMenuItem("About");
		controlPanel = new ControlPanel(this);
		paintPanel = new PaintPanel(this.controlPanel,this.manager);

		if (manager instanceof ServerManager) {
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
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		File f = new File(".dismiss");
		if (!f.exists())
			new HelpDialog().setVisible(true);
	}

	public PaintPanel getPaintPanel() {
		return paintPanel;
	}

	public void repaintPanel() {
	    paintPanel.repaint();
    }

    public void clearPanel() {
        paintPanel.clear();
    }
}
