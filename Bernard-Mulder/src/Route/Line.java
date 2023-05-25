package Route;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Line {
	ArrayList<Point> line = new ArrayList<>();
	double length;

	public ArrayList<Point> getLine() {
		return line;
	}

	public double getLength() {
		return length;
	}

	public Line(Point p1, Point p2){
		line.add(p1);
		line.add(p2);

		int dx = ( line.get(1).getX() ) - ( line.get(0).getX() );
		int dy = ( line.get(1).getY() ) - ( line.get(0).getY() );

		length = Math.sqrt( ( dx * dx ) + ( dy * dy ) );
	}

	@Override
	public String toString() {
		return "Line{" +
				"line=" + line +
				'}';
	}
}
