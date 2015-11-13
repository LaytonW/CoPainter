import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ClientManager implements NetworkManager {

	private Socket s;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	ClientManager(InetAddress host, int p) throws IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(host, p), 5000);
		reader = new ObjectInputStream(s.getInputStream());
		writer = new ObjectOutputStream(s.getOutputStream());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			try {
				Object obj = reader.readObject();
				PaintPanel.paths = (ArrayList<Path>) obj;
				PaintFrame.paintPanel.repaint();
			} catch (java.io.EOFException e) {
				JOptionPane.showMessageDialog(null, "Host is gone!",
						"Connection dropped", JOptionPane.ERROR_MESSAGE);
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Client read: Network failed!\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}
		}
	}

	@Override
	public void write(Object obj) {
		if (!s.isClosed()) {
			try {
				writer.writeObject(obj);
				writer.flush();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Client write: Network failed!\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}
		}
	}
}
