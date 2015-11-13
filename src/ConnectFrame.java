import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.*;

public class ConnectFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String host;
	public String port;
	ConnectFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(315, 170);
		this.setTitle("Connect to CoPainter");
		this.getContentPane().setBackground(Color.WHITE);
		this.getContentPane().setLayout(null);
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
		hostLabel.setBounds(5, 10,50,25);
		portLabel.setBounds(5, 50,50,25);
		hostText.setBounds(60, 10,230,25);
		portText.setBounds(60, 50,230,25);
		serverButton.setBounds(5, 80,140,40);
		clientButton.setBounds(150, 80,160,40);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		class ClientListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					host = hostText.getText();
					port = portText.getText();
					////
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Host");
					return;
				}
				setVisible(false);
				dispose();
				new PaintFrame("Client");
			}
		}
		class ServerListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String port = portText.getText();
					if (Integer.parseInt(port) > 1023 && Integer.parseInt(port) <= 65535)
						;////
					else {
						JOptionPane.showMessageDialog(null, "Invalid Host");
						return;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Host");
					return;
				}
				setVisible(false);
				dispose();
				new PaintFrame("Server");
			}
		}
		clientButton.addActionListener(new ClientListener());
		serverButton.addActionListener(new ServerListener());
	}
}
