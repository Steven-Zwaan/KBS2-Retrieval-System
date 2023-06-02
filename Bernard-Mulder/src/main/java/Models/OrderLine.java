package Models;

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
    private DatabaseConnector databaseConnector = new DatabaseConnector();

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

    // functie om de hoeveelheid van een product binnen een orderline te updaten in de database, op basis van input quantity
    public void setQuantity(int quantity) {
        // controlleer of de nieuwe quanitity groter is dan de huidige; de quantity wordt groter
        if (quantity > this.quantity){
            // controlleer of de voorraad - het bijkomende quantity groter of gelijk is aan 0; anders is er niet genoeg voorraad
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
            // anders wordt de quantity kleiner
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

    // functie om de pickedquantity te veranderen, als de picked quantity groter of gelijk is aan de quanity dan wordt de setPickingCompletedWhen uitgevoerd
    public void setPickedQuantity(int pickedQuantity) {
        String sql = "UPDATE orderlines SET PickedQuantity = ? WHERE StockItemID = ? AND OrderLineID = ?";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, pickedQuantity);
            statement.setInt(2, this.product.getId());
            statement.setInt(3, id);
            int resultSet = statement.executeUpdate();
            if(resultSet > 0){
                this.pickedQuantity = pickedQuantity;
                if (this.pickedQuantity >= this.quantity){
                    setPickingCompletedWhen();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    // functie om de pickedquanitity te verhogen met 1, als de picked quantity groter of gelijk is aan de quanity dan wordt de setPickingCompletedWhen uitgevoerd
    public void increasePickedQuantity() {
        String sql = "UPDATE orderlines SET PickedQuantity = ? WHERE StockItemID = ? AND OrderLineID = ?";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, this.pickedQuantity + 1);
            statement.setInt(2, this.product.getId());
            statement.setInt(3, id);
            int resultSet = statement.executeUpdate();
            if(resultSet > 0){
                this.pickedQuantity = this.pickedQuantity + 1;
                if (this.pickedQuantity >= this.quantity){
                    setPickingCompletedWhen();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    public Timestamp getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    // functie om de pickingcompletedwhen in de database te veranderen naar het huidige moment
    public void setPickingCompletedWhen() {
        String sql = "UPDATE orderlines SET PickingCompletedWhen = ? WHERE StockItemID = ? AND OrderLineID = ?";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, this.product.getId());
            statement.setInt(3, id);
            int resultSet = statement.executeUpdate();
            if(resultSet > 0){
                this.pickingCompletedWhen = new Timestamp(System.currentTimeMillis());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    @Override
    public String toString() {
        return product.getName() + ", aantal: " + quantity;
    }
}
