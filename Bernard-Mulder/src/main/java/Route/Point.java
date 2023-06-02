package Route;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y){ // aanmaken punt
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
