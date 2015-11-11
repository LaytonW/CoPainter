import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class PaintPanel extends JPanel implements MouseMotionListener,MouseListener{
	
	private static final long serialVersionUID = 1L;
	public Point mousePoint;
	PaintPanel () {
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePoint=ControlPanel.current;
		this.add(mousePoint);
		try {
			mousePoint.setLocation(e.getX()-mousePoint.radius/2,e.getY()-mousePoint.radius/2);
//			mousePoint.setLocation(getMousePosition());
		} catch (Exception ex) {
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.remove(mousePoint);
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
