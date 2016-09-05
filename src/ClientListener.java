import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hans on 9/5/16.
 */
public class ClientListener extends ModeListener {

    private InetAddress IP;

    ClientListener(ConnectFrame frame) {
        super(frame);
    }


    @Override
    protected void startPaintFrame() throws IOException {
        ClientManager clientManager = new ClientManager(this.IP,this.port);
        new PaintFrame(clientManager);
    }

    @Override
    protected void parseInput() throws NullPointerException,UnknownHostException {
        super.parseInput();
        this.IP = this.getHost();
    }

    private InetAddress getHost() throws UnknownHostException {
        return InetAddress.getByName(this.hostText.getText());
    }

}
