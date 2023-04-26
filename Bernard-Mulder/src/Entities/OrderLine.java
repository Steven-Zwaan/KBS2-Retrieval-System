package Entities;

import java.sql.Timestamp;

public class OrderLine {
    private int id;
    private Product product;
    private int quantity;
    private int pickedQuantity;
    private Timestamp pickingCompletedWhen;

    public OrderLine(int id, Product product, int quantity, int pickedQuantity, Timestamp pickingCompletedWhen) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.pickedQuantity = pickedQuantity;
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPickedQuantity() {
        return pickedQuantity;
    }

    public Timestamp getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", pickedQuantity=" + pickedQuantity +
                ", pickingCompletedWhen=" + pickingCompletedWhen +
                '}';
    }
}
