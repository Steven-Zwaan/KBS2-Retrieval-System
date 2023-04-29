package Entities;

import Database.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderLine {
    private int id;
    private Product product;
    private int quantity;
    private int pickedQuantity;
    private Timestamp pickingCompletedWhen;
    DatabaseConnector databaseConnector = new DatabaseConnector();

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

    public void setQuantity(int quantity) {
        if (quantity > this.quantity){
            if (this.product.getStock() - (quantity - this.quantity) >= 0 )
            {
                String sql = "UPDATE orderlines SET Quantity = ? WHERE StockItemID = ? AND OrderLineID = ?";
                try {
                    PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
                    statement.setInt(1, quantity);
                    statement.setInt(2, this.product.getId());
                    statement.setInt(3, id);
                    int resultSet = statement.executeUpdate();
                    if(resultSet > 0){
                        this.quantity = quantity;
                        this.product.setStock(this.product.getStock() - (quantity - this.quantity));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    databaseConnector.disconnect();
                }
            }
        } else {
            String sql = "UPDATE orderlines SET Quantity = ? WHERE StockItemID = ? AND OrderLineID = ?";
            try {
                PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
                statement.setInt(1, quantity);
                statement.setInt(2, this.product.getId());
                statement.setInt(3, id);
                int resultSet = statement.executeUpdate();
                if(resultSet > 0){
                    this.quantity = quantity;
                    this.product.setStock(this.product.getStock() + (this.quantity - quantity));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnector.disconnect();
            }
        }
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
