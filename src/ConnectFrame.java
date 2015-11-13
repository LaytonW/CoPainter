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
<<<<<<< HEAD
		serverButton.setBounds(5, 80,140,40);
		clientButton.setBounds(150, 80,160,40);
=======
		hostButton.setBounds(5, 80,140,40);
		guestButton.setBounds(150, 80,160,40);
>>>>>>> refs/remotes/origin/Layton
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		class ClientListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					host = hostText.getText();
					port = portText.getText();
<<<<<<< HEAD
					int index0 = host.indexOf(".");
					int index1 = host.indexOf(".", index0 + 1);
					int index2 = host.indexOf(".", index1 + 1);
					int ip0 = Integer.parseInt(host.substring(0, index0));
					int ip1 = Integer.parseInt(host.substring(index0 + 1, index1));
					int ip2 = Integer.parseInt(host.substring(index1 + 1, index2));
					int ip3 = Integer.parseInt(host.substring(index2 + 1));
					if (Integer.parseInt(port) >=0 && Integer.parseInt(port) <= 65535 && ip0 >= 0 && ip0 <= 255
							&& ip1 >= 0 && ip1 <= 255 && ip2 >= 0 && ip2 <= 255 && ip3 >= 0 && ip3 <= 255) {
							;
					} else if (!(Integer.parseInt(port) >=0 && Integer.parseInt(port) <= 65535)) {
						JOptionPane.showMessageDialog(null, "Invalid Port");
						return;
					}
						
					else if (!(ip0 <= 255 && ip1 >= 0 && ip1 <= 255 && ip2 >= 0 && ip2 <= 255 && ip3 >= 0
							&& ip3 <= 255)) {
						JOptionPane.showMessageDialog(null, "Invalid Host");
						return;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
=======
					////
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Host");
>>>>>>> refs/remotes/origin/Layton
					return;
				}
				setVisible(false);
				dispose();
<<<<<<< HEAD
				PaintFrame paintFrame = new PaintFrame("Client");
=======
				new PaintFrame();
>>>>>>> refs/remotes/origin/Layton
			}
		}
		class ServerListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
<<<<<<< HEAD
					port = portText.getText();
					if (Integer.parseInt(port) >=0 && Integer.parseInt(port) <= 65535) {
						;
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Port");
						return;
					}
						
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Port");
				}
				setVisible(false);
				dispose();
				PaintFrame paintFrame = new PaintFrame("Server");
=======
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
				new PaintFrame();
>>>>>>> refs/remotes/origin/Layton
			}
		}
		clientButton.addActionListener(new ClientListener());
		serverButton.addActionListener(new ServerListener());
	}
}
