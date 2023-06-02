package Models;

public class PickOrder { //in deze klasse worden pickorders opgeslagen zodat deze klaar zijn om gepickt te worden
    private OrderLine orderLine;
    private int xPos;
    private int yPos;
    private int weight;
    private boolean isPicked;
    private int orderNummer;

    private int doos;


    public PickOrder(OrderLine orderLine, int xPos, int yPos, int weight, int orderNummer) { //de pickorder krijgt de bijbehorende orderline mee, de locatie en het gewicht. daarnaast krijgt de pickorder ook een int mee om te bepalen in welke doos deze komt
        this.xPos = xPos;
        this.yPos = yPos;
        this.weight = weight;
        this.isPicked = false;
        this.orderLine = orderLine;
        this.orderNummer = orderNummer;
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

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public int getOrderNummer() {
        return orderNummer;
    }

    public int getDoos() {
        return doos;
    }

    public void setDoos(int doos) {
        this.doos = doos;
    }

    @Override
    public String toString() {
        return "Ordernummer: " + orderNummer + ", Pickorder ID: " + orderLine.getId() + ", X: " + xPos + ", Y: " + yPos + ", gewicht: " + weight;
    }
}
