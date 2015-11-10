import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
public class GUI {
	public String host;
	public String port;
	public ArrayList<Path> paths;
	private JFrame connectFrame;
	GUI() {
		connectFrame=new JFrame();
	}
	public void drawGUI() {
		connectFrame();
	}
	
	private void connectFrame() {
		connectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		connectFrame.setSize(312, 165);
		connectFrame.setTitle("Connect to CoPainter");
		connectFrame.getContentPane().setBackground(Color.WHITE);
		connectFrame.getContentPane().setLayout(null);
		connectFrame.setResizable(false);
		JLabel hostLabel=new JLabel("Host:");
		JLabel portLabel=new JLabel("Port: ");
		JTextField hostText=new JTextField();
		JTextField portText=new JTextField();
		JButton hostButton=new JButton("Start as a host");
		JButton guestButton=new JButton("Connect to a host");
		connectFrame.getContentPane().add(hostLabel);
		connectFrame.getContentPane().add(hostText);
		connectFrame.getContentPane().add(portLabel);
		connectFrame.getContentPane().add(portText);
		connectFrame.getContentPane().add(hostButton);
		connectFrame.getContentPane().add(guestButton);
		hostLabel.setSize(50,25);
		hostLabel.setFont(new Font(null,Font.PLAIN,18));
		portLabel.setSize(50,25);
		portLabel.setFont(new Font(null,Font.PLAIN,18));
		hostButton.setSize(140,40);
		guestButton.setSize(140,40);
		hostText.setSize(230,25);
		portText.setSize(230,25);
		hostLabel.setLocation(5, 10);
		portLabel.setLocation(5, 50);
		hostText.setLocation(60,10);
		portText.setLocation(60,50);
		hostButton.setLocation(5,80);
		guestButton.setLocation(150, 80);
		connectFrame.setVisible(true);
		class GuestListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					host=hostText.getText();
					port=portText.getText();
					int index0=host.indexOf(".");
					int index1=host.indexOf(".", index0+1);
					int index2=host.indexOf(".", index1+1);
					int ip0=Integer.parseInt(host.substring(0, index0));
					int ip1=Integer.parseInt(host.substring(index0+1, index1));
					int ip2=Integer.parseInt(host.substring(index1+1, index2));
					int ip3=Integer.parseInt(host.substring(index2+1));
					if(Integer.parseInt(port)>1023 && Integer.parseInt(port)<=65535&&ip0>=0&&ip0<=255&&ip1>=0&&ip1<=255&&ip2>=0&&ip2<=255&&ip3>=0&&ip3<=255) {
						connectAsGuest();
						connectFrame.setVisible(false);
						connectFrame.dispose();
						paintFrame();
					}
					else if (!(Integer.parseInt(port)>1023 && Integer.parseInt(port)<=65535))
						JOptionPane.showMessageDialog(null, "Invalid Port");
					else if (!(ip0<=255&&ip1>=0&&ip1<=255&&ip2>=0&&ip2<=255&&ip3>=0&&ip3<=255))
						JOptionPane.showMessageDialog(null, "Invalid Host");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
		}
		class HostListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hostText.setText("localhost");
				try {
					String port=portText.getText();
					if (Integer.parseInt(port)>1023 && Integer.parseInt(port)<=65535) {
						connectAsHost();
						connectFrame.setVisible(false);
						connectFrame.dispose();
						paintFrame();
					}
					else
						JOptionPane.showMessageDialog(null, "Invalid Host");
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Host");
				}
			}
			
		}
		guestButton.addActionListener(new GuestListener());
		hostButton.addActionListener(new HostListener());
	}
	public void paintFrame() {
		JFrame paintFrame=new JFrame();
		paintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		paintFrame.setSize(700, 700);
		paintFrame.setTitle("Collaborative Painter");
		paintFrame.setResizable(false);
		paintFrame.setLayout(null);
		JMenuBar menuBar=new JMenuBar();
		JMenu menu=new JMenu("Control");
		JMenuItem clear=new JMenuItem("Clear");
		JMenuItem save=new JMenuItem("Save");
		JMenuItem load=new JMenuItem("Load");
		JMenuItem exit=new JMenuItem("Exit");
		ControlPanel controlPanel=new ControlPanel();
		JPanel board=new JPanel();
		menu.add(clear);
		menu.add(save);
		menu.add(load);
		menu.add(exit);
		menuBar.add(menu);
		paintFrame.getContentPane().add(menuBar);
		menuBar.setLocation(0, 0);
		paintFrame.getContentPane().add(board);
		controlPanel.setMaximumSize(new Dimension(700,100));
		paintFrame.getContentPane().add(controlPanel);
		menuBar.setSize(700, 25);
		board.setSize(700,630);
		board.setBackground(Color.WHITE);
		controlPanel.setSize(700,45);
		controlPanel.setLocation(0, 630);
		controlPanel.setMaximumSize(new Dimension(700,100));
		paintFrame.setVisible(true);
	}
	public void connectAsGuest() {
		//Add here as a host

	}
	public void connectAsHost() {
		//Add here as a guest
	}
}
