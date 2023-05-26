import Models.*;
public class PickOrder {
    private OrderLine orderLine;
    private int xPos;
    private int yPos;
    private int weight;
    private int pickOrderID;
    private boolean isPicked;


    public PickOrder(OrderLine orderLine, int xPos, int yPos, int weight) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.weight = weight;
        this.isPicked = false;
        this.orderLine = orderLine;

    }
    public int getWeight() {
        return weight;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public boolean getPickStatus() {
        return isPicked;
    }

    public void setPicked() {
        isPicked = true;
    }

    @Override
    public String toString() {
        return "Pickorder ID: " + orderLine.getId() + ", X: " + xPos + ", Y: " + yPos + ", gewicht: " + weight;
    }
}
