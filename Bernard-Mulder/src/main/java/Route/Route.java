package Route;import java.util.ArrayList;

public class Route {
	private ArrayList<Line> route = new ArrayList<>();
	private double length;

	public ArrayList<Line> getRoute() {
		return route;
	}

	public double getLength() {
		return length;
	}

	public Route(Line l1, Line l2, Line l3, Line l4){
		route.add(l1);
		route.add(l2);
		route.add(l3);
		route.add(l4);

		length = route.get(0).getLength() + route.get(1).getLength() + route.get(2).getLength() + route.get(3).getLength();
	}

	public Route(Line l1, Line l2, Line l3){
		route.add(l1);
		route.add(l2);
		route.add(l3);

		length = route.get(0).getLength() + route.get(1).getLength() + route.get(2).getLength();
	}



	@Override
	public String toString() {
		return "Route{" +
				"route=" + route +
				'}';
	}
}
