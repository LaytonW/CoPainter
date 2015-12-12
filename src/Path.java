import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Path implements Serializable {

	private static final long serialVersionUID = 1L;
	public CopyOnWriteArrayList<Point> points=new CopyOnWriteArrayList<Point>();
	public Color color;
	public int radius;
	public boolean instance;
	public int author;
	Path (Color c,int r) {
		color=c;
		radius=r;
		instance=false;
	}
	
	Path (Path p) {
		color = p.color;
		radius = p.radius;
		for (Point point : p.points)
			points.add((Point) point.clone());
		instance=p.instance;
	}
	
	@Override
	public boolean equals(Object obj) {
		Path p = (Path) obj;
		boolean samePoints = points.size() == p.points.size();
		if (samePoints)
			for (int i = 0; i < points.size(); ++i)
				if (!points.get(i).equals(p.points.get(i)))
					samePoints = false;
		return color.equals(p.color) && radius == p.radius && samePoints&&instance==p.instance;
	}
}