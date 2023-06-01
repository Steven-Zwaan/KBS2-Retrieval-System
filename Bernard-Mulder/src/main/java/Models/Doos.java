package Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Doos {

    private int gewicht = 10;
    private ArrayList<PickOrder> pickOrders = new ArrayList<>();

    public ArrayList<PickOrder> getPickOrders() {
        return pickOrders;
    }

    public boolean addPickOrder(PickOrder pickOrder) {
        if (gewicht >= pickOrder.getWeight()) {
            pickOrders.add(pickOrder);
            gewicht -= pickOrder.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public int getGewicht() {
        return gewicht;
    }
}
