import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class HelpDialog extends JDialog {
	
	private int index = 0;
	final private static String[] helpMessages = {
			"Welcome to CoPainter!<br>"
			+ "In this quick guide, you will see how to "
			+ "draw beautiful paintings with your friends "
			+ "through the Internet, with brush srokes "
			+ "supporting full-color and a wide range of sizes. "
			+ "Click 'Next' to start.",
			
			"Connection:<br>"
			+ "If you start the CoPainter as a server/host, "
			+ "you can click 'Help -> About' in the menu bar "
			+ "to get the connection details to tell your friends "
			+ "to connect to you. You may also check the number "
			+ "of connections there.",
			
			"Connection:<br>"
			+ "To start the CoPainter as a client, "
			+ "you need to type in the IP address as well as "
			+ "the port of the server in the start-up connection prompt. "
			+ "Once connected, the main frame will appear. "
			+ "You can access 'Help -> About' in the menu bar "
			+ "to check current connection status. "
			+ "You can reconnect to another host server "
			+ "via 'Control -> Reconnection' in the menu bar "
			+ "when the host is gone or you want to change the server.",
			
			"Painting:<br>"
			+ "In the main frame, you can draw lines by "
			+ "easily clicking or dragging your mouse. "
			+ "The cursor is the preview of your current brush stroke.",
			
			"Painting:<br>"
			+ "You can quickly change the color of your brush "
			+ "to one of the preset colors at bottom left "
			+ "by simply clicking it. You can also customize the color "
			+ "by clicking the 'Custom' button and pick your favorite color "
			+ "in the color chooser.",
			
			"Painting:<br>"
			+ "You can also quickly change the size of the brush "
			+ "by clicking the preset sizes at bottom right. "
			+ "You may also directly input the size you want "
			+ "in the text field and click 'Set' or press Enter. "
			+ "The '+1'/'-1' buttons can be used to quickly adjust "
			+ "the size. You can press the buttons and hold your mouse "
			+ "to continuously increase or decrease the size.",
			
			"Control:<br>"
			+ "If you want to save your masterpiece, simply click "
			+ "'Control -> Save' in the menu bar to save the .pb "
			+ "painting board file to your computer. "
			+ "The host can load .pb files to the painting board "
			+ "and to all the clients via 'Control -> Load' in the menu bar. "
			+ "The host can also clear the whole board "
			+ "via 'Control -> Clear' in the menu bar.",
			
			"That's all! Hope you enjoy using the CoPainter.<br>"
			+ "Click 'Finish' to start painting."
	};
	private JButton previous;
	private JButton next;
	private JButton close;
	private JLabel helpMessage;
	private JCheckBox dismiss;
	
	HelpDialog() {
		setTitle("Help");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		
		previous = new JButton("Previous");
		next = new JButton("Next");
		close = new JButton("Close");
		helpMessage = new JLabel();
		dismiss = new JCheckBox("Don't show this again");
		
		File f = new File(".dismiss");
		if (f.exists())
			dismiss.setSelected(true);
		else
			dismiss.setSelected(false);
		
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPrevious();
			}
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});
		close.addActionListener(new ExitListener());
		
		helpMessage.setPreferredSize(new Dimension(380, 150));
		dismiss.setPreferredSize(new Dimension(300, 20));
		previous.setPreferredSize(new Dimension(100, 20));
		next.setPreferredSize(new Dimension(100, 20));
		close.setPreferredSize(new Dimension(100, 20));
		
		SpringLayout layout = new SpringLayout();
		this.getContentPane().setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, helpMessage, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, helpMessage, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, dismiss, 5, SpringLayout.SOUTH, helpMessage);
		layout.putConstraint(SpringLayout.WEST, dismiss, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, previous, 5, SpringLayout.SOUTH, dismiss);
		layout.putConstraint(SpringLayout.NORTH, next, 5, SpringLayout.SOUTH, dismiss);
		layout.putConstraint(SpringLayout.NORTH, close, 5, SpringLayout.SOUTH, dismiss);
		layout.putConstraint(SpringLayout.WEST, previous, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, next, 5, SpringLayout.EAST, previous);
		layout.putConstraint(SpringLayout.WEST, close, 5, SpringLayout.EAST, next);
		
		this.getContentPane().add(helpMessage);
		this.getContentPane().add(dismiss);
		this.getContentPane().add(previous);
		this.getContentPane().add(next);
		this.getContentPane().add(close);
		
		showNext();
	}
	
	private void showNext() {
		switch (index) {
		case 8:
			return;
		case 7:
			next.setText("Finish");
			next.removeActionListener(next.getActionListeners()[0]);
			next.addActionListener(new ExitListener());
			dismiss.setVisible(true);
			break;
		case 0:
			previous.setEnabled(false);
			dismiss.setVisible(false);
			break;
		case 1:
			previous.setEnabled(true);
			break;
		default:
			next.setText("Next");
			dismiss.setVisible(false);
			next.removeActionListener(next.getActionListeners()[0]);
			next.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					showNext();
				}
			});
		}
		helpMessage.setText("<html>" + helpMessages[index++] + "</html>");
	}
	
	private void showPrevious() {
		index -= 2;
		showNext();
	}
	
	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (dismiss.isSelected()) {
				File f = new File(".dismiss");
				try {
					f.createNewFile();
				} catch (IOException e1) {}
			}
			setVisible(false);
			dispose();
		}
	}
}
