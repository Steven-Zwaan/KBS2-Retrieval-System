package Models;

import Database.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orderList = new ArrayList<>();
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    // Functie om alle orders uit de database te halen, het resultaat vult de orderList ArrayList
    public void getOrdersFromDatabase(){
        String sql = "SELECT orders.OrderID , orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, " +
                "cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, customers.DeliveryPostalCode, customers.PhoneNumber " +
                "FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` ON customers.DeliveryCityID = cities.CityID " +
                "JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID WHERE `PickingCompletedWhen` IS NULL ORDER BY OrderID ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            this.orderList.clear();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"),
                        resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"),
                        resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"), resultSet.getString("PhoneNumber"));
                this.orderList.add(new Order(resultSet.getInt("OrderID"), customer, resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    // Functie om één order uit de database te halen op basis van het input id, het resultaat vult de orderList ArrayList
    public void getOrderFromDatabase(int id){
        String sql = "SELECT orders.OrderID, orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, " +
                "cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, " +
                "customers.DeliveryPostalCode FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` " +
                "ON customers.DeliveryCityID = cities.CityID JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID " +
                "WHERE `PickingCompletedWhen` IS NULL AND orders.OrderID = ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            this.orderList.clear();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"),
                        resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"),
                        resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"), resultSet.getString("PhoneNumber"));
                this.orderList.add(new Order(resultSet.getInt("OrderID"), customer, resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }



}
