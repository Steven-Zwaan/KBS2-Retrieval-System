package Entities;

import Database.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Orders {
    private ArrayList<Order> orders = new ArrayList<>();
    DatabaseConnector databaseConnector = new DatabaseConnector();

    public void storeOrdersFromDatabase(){
        String sql = "SELECT orders.OrderID, orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, " +
                "cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, customers.DeliveryPostalCode " +
                "FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` ON customers.DeliveryCityID = cities.CityID " +
                "JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID WHERE `PickingCompletedWhen` IS NULL ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            this.orders.clear();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"),
                        resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"),
                        resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"));
                this.orders.add(new Order(resultSet.getInt("OrderID"), customer, resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    public void storeOrderFromDatabase(int id){
        String sql = "SELECT orders.OrderID, orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, " +
                "cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, " +
                "customers.DeliveryPostalCode FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` " +
                "ON customers.DeliveryCityID = cities.CityID JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID " +
                "WHERE `PickingCompletedWhen` IS NULL AND orders.OrderID = ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            this.orders.clear();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"),
                        resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"),
                        resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"));
                this.orders.add(new Order(resultSet.getInt("OrderID"), customer, resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
