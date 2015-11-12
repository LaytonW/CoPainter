import java.awt.event.*;
public class MenuListener implements ActionListener{

	public String Item;
	MenuListener (String i) {
		Item=i;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (Item=="clear") {
			PaintPanel.paths.clear();
			PaintFrame.paintPanel.repaint();
		}
		else if (Item=="exit") {
			System.exit(0);
		}
		else if (Item=="save") {
			
		}
		else if (Item=="load") {
			
		}
	}
}
