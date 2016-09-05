import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager extends NetworkManager {

	private Socket s;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private String host;

	ClientManager(InetAddress host, int p) throws IOException {
		super(p);
		this.host=host.toString();
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
					this.frame.clearPanel();
				else if (obj instanceof CopyOnWriteArrayList<?>)
					PaintPanel.buffer = (CopyOnWriteArrayList<Path>) obj;
				else if (obj instanceof Path)
					if (!PaintPanel.buffer.contains(obj))
						PaintPanel.buffer.add((Path) obj);
				this.frame.repaintPanel();
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
				System.exit(0);
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


	public String getHost() {
		return host;
	}
}
