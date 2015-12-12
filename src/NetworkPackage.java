

import java.awt.Point;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkPackage {
	private int type; // 1:point 2: path 3:ID
	private int author; //
	private Point point;
	private int ID;
	private Path path;
	boolean clear;
	NetworkPackage(NetworkPackage another) {
		this.type=another.getType();
		this.author=another.getAuthor();
		this.path=new Path(another.getPath());
		this.point=another.getPoint();
		this.clear=another.getClear();
		this.ID=another.getID();
	}
	NetworkPackage(Point p,int a){
		point=p;
		type=1;
		author=a;
	}
	NetworkPackage(Path path,int a){
		this.path=new Path(path);
		type=2;
		author=a;
	}
	NetworkPackage(int ID,int a) {
		this.ID=ID;
		type=4;
		author=a;
	}
	public int getType() {
		return type;
	}
	public int getAuthor() {
		return author;
	}
	public Point getPoint() {
		return point;
	}
	public Path getPath() {
		return path;
	}
	public void setID(int i) {
		this.ID=i;
	}
	public int getID() {
		return ID;
	}
	public void setClear(boolean c) {
		this.clear=c;
	}
	public boolean getClear() {
		return clear;
	}
	
}
