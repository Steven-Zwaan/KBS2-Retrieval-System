package Route;

import java.util.ArrayList;

public class Bruteforce {
	ArrayList<Route> options = new ArrayList<>();
	double shortestLenght;
	Route shortestRoute;

	public Bruteforce(Point start, Point p1, Point p2, Point p3, Point end){


		Line start1 = new Line(start, p1);
		Line start2 = new Line(start, p2);
		Line start3 = new Line(start, p3);

		Line p1_p2 = new Line(p1, p2);
		Line p1_p3 = new Line(p1, p3);

		Line p2_p1 = new Line(p2, p1);
		Line p2_p3 = new Line(p2, p3);

		Line p3_p1 = new Line(p3, p1);
		Line p3_p2 = new Line(p3, p2);

		Line end1 = new Line(p1, end);
		Line end2 = new Line(p2, end);
		Line end3 = new Line(p3, end);

		Route r1 = new Route(start1, p1_p2, p2_p3, end3);
		Route r2 = new Route(start1, p1_p3, p3_p2, end2);

		Route r3 = new Route(start2, p2_p1, p1_p3, end3);
		Route r4 = new Route(start2, p2_p3, p3_p1, end1);

		Route r5 = new Route(start3, p3_p2, p2_p1, end1);
		Route r6 = new Route(start3, p3_p1, p1_p2, end2);

		options.add(r1);
		options.add(r2);
		options.add(r3);
		options.add(r4);
		options.add(r5);
		options.add(r6);
		shortestLenght = options.get(0).getLength();
		shortestRoute = options.get(0);
	}

	public Bruteforce(Point start, Point p1, Point p2, Point end){
		Line start1 = new Line(start, p1);
		Line start2 = new Line(start, p2);

		Line p1_p2 = new Line(p1, p2);
		Line p2_p1 = new Line(p2, p1);

		Line end1 = new Line(p1, end);
		Line end2 = new Line(p2, end);

		Route r1 = new Route(start1, p1_p2, end2);
		Route r2 = new Route(start1, p2_p1, end1);

		options.add(r1);
		options.add(r2);
		shortestLenght = options.get(0).getLength();
		shortestRoute = options.get(0);
	}

	public ArrayList<Point> calc(){
		for (int i = 0; i < options.size(); i++){
			if(shortestLenght > options.get(i).getLength()) {
				shortestLenght = options.get(i).getLength();
				shortestRoute = options.get(i);
			}
		}
		ArrayList<Point> points = new ArrayList<>();
		for (int i = 1; i < shortestRoute.getRoute().size(); i++){
			points.add(shortestRoute.getRoute().get(i).getLine().get(0));
		}


		return points;
	}
}


