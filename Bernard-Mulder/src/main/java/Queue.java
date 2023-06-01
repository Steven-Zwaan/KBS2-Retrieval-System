import Route.Point;


import java.io.IOException;
import java.util.ArrayList;

public class Queue {
	ArrayList<ArrayList<Route.Point>> queue = new ArrayList<>(); // arraylist met routes
	boolean active = false;
	boolean messageNotSend = true;
	public static Queue instance = null; // instance opslaan

	public static Queue getInstance() { // instance ophalen
		if(instance == null) { // als er nog geen instance is een nieuwe aanmaken

			instance = new Queue();
		}
		return instance;
	}

	private Queue() {

		ArduinoConnection arduino = new ArduinoConnection("COM8");

		arduino.openPort(); // Openen arduino port
		arduino.receiveData(); // Check for message
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		new Thread(() -> {
			while(true) {
				if (active){
					if (messageNotSend){
						for (int i = 0; i < queue.get(0).size(); i++) {
							try {
								arduino.sendData((byte) 3, (byte) queue.get(0).get(i).getX(), (byte) queue.get(0).get(i).getY());
							} catch (IOException e) {
								throw new RuntimeException(e);
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}
							System.out.println(queue.get(0).get(i));
						}
						queue.remove(0);
						WeergavePanel.pickedOrderNummers.remove(0);
						WeergavePanel.removeOrderFromQueue(WeergavePanel.pickedOrderNummers.get(0));
						messageNotSend = false;
						scherm.weergavePanel.refreshPanel();
						scherm.packingScreen.refreshPanel();
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				checkQueue();
			}
		}).start();
	}

	public void AddQueue(ArrayList<Point> PickupRoute) { // toevoegen van route aan wachtrij
		queue.add(PickupRoute);
//		System.out.println(queue.get(0));
	}

	public void checkQueue(){ // controleren of er iets in de wachtrij zit
		if (queue.size() == 0) {
			active = false;
		} else {
			active = true;
		}
	}

	public void setScreen(MainScreen scherm) {
		this.scherm = scherm;
	}
}
