import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class PaintPanel extends JPanel implements MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	public volatile static CopyOnWriteArrayList<Path> buffer;
	private Path currentPath;
	private NetworkManager networkManager;
	
	PaintPanel (NetworkManager n) {
		networkManager = n;
		new Thread(networkManager).start();
		buffer=new CopyOnWriteArrayList<Path>();
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
	}
	
	public void clear() {
		buffer = new CopyOnWriteArrayList<Path>();
		if (networkManager instanceof ServerManager)
			networkManager.write("clear");
	}
	
	public synchronized void loadToNetwork() {
		networkManager.write(new CopyOnWriteArrayList<Path>(buffer));
	}
	
	public synchronized void updateNetwork() {
		networkManager.write(new Path(currentPath));
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (Path path : buffer) {
			g.setColor(path.color);
			if (path.points.size()==1)
				g.fillOval(path.points.get(0).x-path.radius,path.points.get(0).y-path.radius,path.radius*2,path.radius*2);
			else {
				if(g instanceof Graphics2D) {
					Graphics2D g2D=(Graphics2D) g;
					g2D.setStroke(new BasicStroke(path.radius*2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
				}
				Point prevPoint=null;
				for (Point p:path.points) {
					if(prevPoint!=null) {
						g.drawLine(prevPoint.x, prevPoint.y, p.x, p.y);
					}
					prevPoint=p;
				}
			}
		}
		if (currentPath != null) {
			g.setColor(currentPath.color);
			if(g instanceof Graphics2D) {
				Graphics2D g2D=(Graphics2D) g;
				g2D.setStroke(new BasicStroke(currentPath.radius*2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
			}
			Point prevPoint=null;
			for (Point p:currentPath.points) {
				if(prevPoint!=null) {
					g.drawLine(prevPoint.x, prevPoint.y, p.x, p.y);
				}
				prevPoint=p;
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		currentPath.points.add(new Point(e.getPoint()));
		mouseMoved(e);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
			ControlPanel.current.setLocation(e.getX()-ControlPanel.current.getRadius(),e.getY()-ControlPanel.current.getRadius());
		} catch (Exception ex) {
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		this.setCursor(blankCursor);
		ControlPanel.current.setSize(ControlPanel.current.getRadius()*2, ControlPanel.current.getRadius()*2);
		this.add(ControlPanel.current);
		try {
			ControlPanel.current.setLocation(arg0.getX()-ControlPanel.current.getRadius(),arg0.getY()-ControlPanel.current.getRadius());
		} catch (Exception ex) {
		}
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		remove(ControlPanel.current);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (currentPath != null && !currentPath.points.isEmpty()) {
			buffer.add(new Path(currentPath));
			updateNetwork();
		}
		currentPath = new Path(ControlPanel.current.getColor(),ControlPanel.current.getRadius());
		if (getMousePosition() != null)
			currentPath.points.add(new Point(getMousePosition()));
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		buffer.add(new Path(currentPath));
		updateNetwork();
		currentPath = new Path(ControlPanel.current.getColor(),ControlPanel.current.getRadius());
	}
}
