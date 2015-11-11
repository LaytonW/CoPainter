import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import java.awt.event.*;
public class ColorChooserFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColorChooserFrame() {
		this.setSize(650,440);
		this.setLayout(null);
		this.setTitle("Color Chooser");
		JColorChooser colorChooser=new JColorChooser();
		JButton confirm=new JButton("Confirm");
		this.add(colorChooser);
		this.add(confirm);
		colorChooser.setBounds(0, 0, 650, 350);
		confirm.setBounds(0, 350, 650, 50);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ControlPanel.current.setPointColor(colorChooser.getColor());
				setVisible(false);
				dispose();
			}
			
		});
		this.setVisible(true);
	}
}
