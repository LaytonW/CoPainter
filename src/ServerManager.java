import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

public class ServerManager implements NetworkManager {

	private ServerSocket ss;
	private ArrayList<ObjectOutputStream> writers;
	private int clientNumber=0;
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
				write(writer,Integer.toString(getClientNumber()+1));
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

	public synchronized void write(ObjectOutputStream writer, Object obj) {
		try {
			writer.writeObject(obj);
			writer.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server: Cannot write to stream\n"
					+ e.toString(),
					"Network failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	public int getClientNumber() {
		return clientNumber;
	}
	private class ClientHandler implements Runnable {
		
		private Socket s;
		private ObjectInputStream reader;
		private ObjectOutputStream writer;
		
		public ClientHandler(Socket s, ObjectOutputStream w) {
			this.s = s;
			++clientNumber;
			try {
				writer = w;
				reader = new ObjectInputStream(this.s.getInputStream());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Server: Cannot get socket stream.\n"
						+ e.toString(),
						"Network failed", JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					Object obj = reader.readObject();
					if (obj instanceof Path) {
						if(((Path)obj).instance==false) {
							PaintPanel.buffer.add((Path) obj);
							PaintPanel.instant=new CopyOnWriteArrayList<Path>();
						}
						PaintPanel.instant.set(((Path)obj).author, (Path)obj);
					}
					else if (obj instanceof MyPoint) {
						PaintPanel.instant.get(((MyPoint)obj).getAuthor()).points.add(((MyPoint)obj).getPoint());

					}
					PaintFrame.paintPanel.repaint();
					write(obj);
				}
			} catch (Exception e) {
				try {
					s.close();
					writers.remove(writer);
					--clientNumber;
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

