package Entities;

import Database.DatabaseConnector;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {
    private int id;
    private Customer customer;
    private Timestamp date;
    private Timestamp pickingCompletedWhen;
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public Order(int id, Customer customer, Timestamp date, Timestamp pickingCompletedWhen) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Timestamp getDate() {
        return date;
    }

    public Timestamp getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public ArrayList<OrderLine> getOrderLines(){
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        String sql = "SELECT * FROM `orderlines` JOIN `stockitems` ON orderlines.StockItemID = stockitems.StockItemID JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE OrderID = ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Product product = new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand"));
                orderLines.add(new OrderLine(resultSet.getInt("OrderLineID"), product, resultSet.getInt("Quantity"), resultSet.getInt("pickedQuantity"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
        return orderLines;
    }

    public boolean setPickingCompletedWhen() {
        String sql = "UPDATE orders SET PickingCompletedWhen = ? WHERE OrderID = ?";
        int pickingCompletedWhenNullCount = 0;
        for (OrderLine ol: getOrderLines()){
            if (ol.getPickingCompletedWhen() == null) {
                pickingCompletedWhenNullCount++;
            }
        }
        if (pickingCompletedWhenNullCount == 0) {
            try {
                PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.setInt(2, id);
                int resultSet = statement.executeUpdate();
                if(resultSet > 0){
                    this.pickingCompletedWhen = new Timestamp(System.currentTimeMillis());
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                databaseConnector.disconnect();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order nummer: " + id + " postcode: " + customer.getPostalCode() + " adres: " + customer.getAddressLine2();
    }
}
