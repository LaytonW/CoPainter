import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager implements NetworkManager {

	private Socket s;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private volatile boolean running=true;
	private int ID;
	ClientManager(InetAddress host, int p) throws IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(host, p), 5000);
		reader = new ObjectInputStream(s.getInputStream());
		writer = new ObjectOutputStream(s.getOutputStream());
	}
	public void setID(int i) {
		ID=i;
	}
	public int getID() {
		return ID;
	}
	@SuppressWarnings("unchecked")
	@Override

	
	public void run() {
		while (running) {
			try {
				Object obj = reader.readObject();
				if(obj instanceof String) {
					if (obj.toString().equals("clear"))
						PaintFrame.paintPanel.clear();
					else
						this.setID(Integer.parseInt((String)obj));
				}
				if (obj instanceof CopyOnWriteArrayList<?>)
					PaintPanel.buffer = (CopyOnWriteArrayList<Path>) obj;
				else if (obj instanceof Path) {
					if(((Path)obj).instance==false) {
						if (!PaintPanel.buffer.contains(obj))
							PaintPanel.buffer.add((Path) obj);
						PaintPanel.instant=new CopyOnWriteArrayList<Path>();
					}
					else {
						PaintPanel.instant.add(((Path)obj).author,(Path)obj);
					}
				}
				else if (obj instanceof MyPoint) {
					PaintPanel.instant.get(((MyPoint)obj).getAuthor()).points.add(((MyPoint)obj).getPoint());
				}
				PaintFrame.paintPanel.repaint();
			} catch (Exception e) {
				if(running==true)
					e.printStackTrace();
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
				System.out.println(obj);
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
	public void stop() {
		running=false;
		try {
			s.close();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Client: Cannot close socket connection.\n"
					+ e1.toString(),
					"Network failed", JOptionPane.ERROR_MESSAGE);
		}
	}
}
