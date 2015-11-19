import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.concurrent.CopyOnWriteArrayList;

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
				if (obj.toString().equals("clear"))
					PaintFrame.paintPanel.clear();
				else if (obj instanceof CopyOnWriteArrayList<?>)
					PaintPanel.buffer = (CopyOnWriteArrayList<Path>) obj;
				else if (obj instanceof Path)
					PaintPanel.buffer.add((Path) obj);
				PaintFrame.paintPanel.repaint();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Host is gone!",
						"Connection dropped", JOptionPane.ERROR_MESSAGE);
				try {
					s.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Client: Cannot close socket connection.\n"
							+ e.toString(),
							"Network failed", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Client: Cannot write to stream.\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
				try {
					s.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Client: Cannot close socket connection.\n"
							+ e.toString(),
							"Network failed", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}
	}
}
