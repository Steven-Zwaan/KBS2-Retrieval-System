package Models;

import Database.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int id;
    private String name;
    private int stock;
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Product(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStock() {
        return stock;
    }
    // FUnctie om de stock te updated in de database, met de input stock
    public void setStock(int stock) {
        String sql = "UPDATE stockitemholdings SET QuantityOnHand = ? WHERE StockItemID = ?";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, stock);
            statement.setInt(2, id);
            int resultSet = statement.executeUpdate();
            if(resultSet > 0){
                this.stock = stock;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }
    // functie om het object voorraad te updaten met de laatste data van de database
    public void setStockFromDatabase() {
        String sql = "GET QuantityOnHand FROM stockitemholdings WHERE StockItemID = ?";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                this.stock = resultSet.getInt("QuantityOnHand");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }


    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " stock: " + stock;
    }
}
