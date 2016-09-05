import javax.swing.*;
import java.io.IOException;

/**
 * Created by hans on 9/5/16.
 */
public class ServerListener extends ModeListener {


    ServerListener(ConnectFrame frame) {
        super(frame);
    }

    @Override
    protected void startPaintFrame() throws IOException {
        ServerManager serverManager = new ServerManager(this.port);
        new PaintFrame(serverManager);
    }
}
