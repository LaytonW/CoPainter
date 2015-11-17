import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.*;

public class ConnectFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public String host;
	public String port;
	ConnectFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(340, 155);
		this.setTitle("Connect to CoPainter");
		this.getContentPane().setBackground(Color.WHITE);
		SpringLayout connectLayout = new SpringLayout();
		this.getContentPane().setLayout(connectLayout);
		this.setResizable(false);
		JLabel hostLabel = new JLabel("Host:");
		JLabel portLabel = new JLabel("Port: ");
		JTextField hostText = new JTextField("localhost");
		JTextField portText = new JTextField("2333");
		JButton serverButton = new JButton("Start as a host");
		JButton clientButton = new JButton("Connect to a host");
		this.getContentPane().add(hostLabel);
		this.getContentPane().add(hostText);
		this.getContentPane().add(portLabel);
		this.getContentPane().add(portText);
		this.getContentPane().add(serverButton);
		this.getContentPane().add(clientButton);
		hostLabel.setFont(new Font(null, Font.PLAIN, 18));
		portLabel.setFont(new Font(null, Font.PLAIN, 18));
		hostLabel.setPreferredSize(new Dimension(50, 25));
		portLabel.setPreferredSize(new Dimension(50, 25));
		hostText.setPreferredSize(new Dimension(270, 25));
		portText.setPreferredSize(new Dimension(270, 25));
		serverButton.setPreferredSize(new Dimension(160, 40));
		clientButton.setPreferredSize(new Dimension(160, 40));
		connectLayout.putConstraint(SpringLayout.NORTH, hostLabel, 10, SpringLayout.NORTH, this);
		connectLayout.putConstraint(SpringLayout.NORTH, hostText, 10, SpringLayout.NORTH, this);
		connectLayout.putConstraint(SpringLayout.WEST, hostLabel, 5, SpringLayout.WEST, this);
		connectLayout.putConstraint(SpringLayout.WEST, hostText, 5, SpringLayout.EAST, hostLabel);
		connectLayout.putConstraint(SpringLayout.NORTH, portLabel, 10, SpringLayout.SOUTH, hostLabel);
		connectLayout.putConstraint(SpringLayout.NORTH, portText, 10, SpringLayout.SOUTH, hostText);
		connectLayout.putConstraint(SpringLayout.WEST, portLabel, 5, SpringLayout.WEST, this);
		connectLayout.putConstraint(SpringLayout.WEST, portText, 5, SpringLayout.EAST, portLabel);
		connectLayout.putConstraint(SpringLayout.NORTH, serverButton, 10, SpringLayout.SOUTH, portLabel);
		connectLayout.putConstraint(SpringLayout.NORTH, clientButton, 10, SpringLayout.SOUTH, portText);
		connectLayout.putConstraint(SpringLayout.WEST, serverButton, 5, SpringLayout.WEST, this);
		connectLayout.putConstraint(SpringLayout.WEST, clientButton, 5, SpringLayout.EAST, serverButton);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		class ClientListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientManager clientManager;
				try {
					host = hostText.getText();
					port = portText.getText();
					clientManager = new ClientManager(InetAddress.getByName(host), Integer.parseInt(port));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(rootPane, "Unable to connect to host!",
							"Fail to start", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setVisible(false);
				dispose();
				new PaintFrame(clientManager,Integer.parseInt(port),host);
			}
		}
		class ServerListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int port;
				try {
					 port = Integer.parseInt(portText.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(rootPane, "Invalid input!",
							"Invalid input", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ServerManager serverManager;
				try {
					serverManager = new ServerManager(port);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(rootPane, "Unable to listen to port "
							+ String.valueOf(port) +"!",
							"Fail to start", JOptionPane.ERROR_MESSAGE);
					return;
				}
				setVisible(false);
				dispose();
				new PaintFrame(serverManager,port,host);
			}
		}
		clientButton.addActionListener(new ClientListener());
		serverButton.addActionListener(new ServerListener());
	}
}
