import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOError;
import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;
/**
 * Created by hans on 9/5/16.
 */
public abstract class ModeListener implements ActionListener{

    private JFrame parentFrame;
    private JTextField portText;
    protected JTextField hostText;
    protected int port;


    ModeListener(ConnectFrame frame) {
        this.portText = frame.getPortText();
        this.hostText = frame.getHostText();
        parentFrame=frame;
    }

    public void actionPerformed(ActionEvent arg0) {
        try {
            this.parseInput();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(parentFrame.getRootPane(), "Unknown Host",
                    "host unknown", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(parentFrame.getRootPane(), "Invalid input!",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            this.startPaintFrame();
            this.clearParentFrame();
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(parentFrame.getRootPane(), "Unable to connect",
                    "IO Exception", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (BindException e) {
            JOptionPane.showMessageDialog(parentFrame.getRootPane(), "Port already in use",
                    "IO Exception", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame.getRootPane(), "Unable to connect",
                    "IO Exception", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }

    private String getPort() throws  NullPointerException{
        return portText.getText();
    }

    protected abstract void startPaintFrame() throws IOException;

    protected void parseInput() throws NullPointerException,UnknownHostException{
        port = Integer.parseInt(this.getPort());
    }

    private void clearParentFrame(){
        parentFrame.setVisible(false);
        parentFrame.dispose();
    }
}
