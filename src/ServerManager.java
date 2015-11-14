import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServerManager implements NetworkManager {

	private ServerSocket ss;
	private ArrayList<ObjectOutputStream> writers;
	
	ServerManager(int p) throws IOException {
		ss = new ServerSocket(p);
		writers = new ArrayList<ObjectOutputStream>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket s = ss.accept();
				ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
				writers.add(writer);
				write(writer, PaintPanel.buffer);
				new Thread(new ClientHandler(s, writer)).start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Server: Connection failed!\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void write(Object obj) {
		for (ObjectOutputStream writer : writers)
			write(writer, obj);
	}

	public void write(ObjectOutputStream writer, Object obj) {
		try {
			writer.writeObject(obj);
			writer.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server: Cannot write to stream\n"
					+ e.toString(),
					"Network failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class ClientHandler implements Runnable {
		
		private Socket s;
		private ObjectInputStream reader;
		private ObjectOutputStream writer;
		
		public ClientHandler(Socket s, ObjectOutputStream w) {
			this.s = s;
			try {
				writer = w;
				reader = new ObjectInputStream(this.s.getInputStream());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Server: Cannot get socket stream.\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				while (true) {
					Object obj = reader.readObject();
					if (obj instanceof ArrayList<?>)
						PaintPanel.buffer = (ArrayList<Path>) obj;
					PaintFrame.paintPanel.repaint();
					write(obj);
				}
			} catch (Exception e) {
				try {
					s.close();
					writers.remove(writer);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Server: Cannot reset socket connection.\n"
							+ e.toString(),
							"Network failed", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}
	}
}

