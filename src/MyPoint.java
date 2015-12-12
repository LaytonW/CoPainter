import java.awt.Point;
import java.io.Serializable;

public class MyPoint implements Serializable{
	/**
	 * 
	 */
	private int author;
	private Point point;
	MyPoint(MyPoint another) {
		this.author=another.author;
		this.point=another.point;
	}
	MyPoint(int author,Point point) {
		this.author=author;
		this.point=point;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int i) {
		author=i;
	}
	public void setPoint(Point P) {
		this.point=P;
	}
	public Point getPoint() {
		return this.point;
	}
}
