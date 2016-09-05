

public abstract class NetworkManager implements Runnable{

	protected PaintFrame frame;
	protected int port;

	NetworkManager(int port) {
        this.port=port;
	}

	public void setFrame(PaintFrame frame) {
	    this.frame=frame;
    }

	public abstract void write(Object obj);

    public int getPort() {
        return port;
    }
}