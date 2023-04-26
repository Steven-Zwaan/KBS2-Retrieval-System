package Database;

import Entities.Customer;
import Entities.Order;
import Entities.OrderLine;
import Entities.Product;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    // Init for database
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private ArrayList<Product> products;
    private ArrayList<Order> orders;
    private ArrayList<OrderLine> orderLines;
    public ArrayList<Product> getProducts(){
        products = new ArrayList<>();
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                this.products.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
//        for (Product p: products){
//            System.out.println(p.toString());
//        }
        return products;
    }
    public ArrayList<Product> getProduct(int id){
        products = new ArrayList<>();
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE stockitems.StockItemID LIKE ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                this.products.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
//        for (Product p: products){
//            System.out.println(p.toString());
//        }
        return products;
    }
    public ArrayList<Product> getProduct(String name) {
        products = new ArrayList<>();
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE stockitems.StockItemName LIKE ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.products.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
//        for (Product p: products){
//            System.out.println(p.toString());
//        }
        return products;
    }

    public ArrayList<Order> getOrders(){
        orders = new ArrayList<>();
        String sql = "SELECT orders.OrderID, orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, customers.DeliveryPostalCode FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` ON customers.DeliveryCityID = cities.CityID JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID WHERE `PickingCompletedWhen` IS NULL ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"), resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"), resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"));
                this.orders.add(new Order(resultSet.getInt("OrderID"), customer,resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
        for (Order o: orders){
            System.out.println(o.toString());
        }
        return orders;
    }
    public ArrayList<Order> getOrder(int id){
        orders = new ArrayList<>();
        String sql = "SELECT orders.OrderID, orders.OrderDate, orders.PickingCompletedWhen, customers.CustomerID, customers.CustomerName, cities.CityName, stateprovinces.StateProvinceName, customers.DeliveryAddressLine1, customers.DeliveryAddressLine2, customers.DeliveryPostalCode FROM `orders` JOIN `customers` ON orders.CustomerID = customers.CustomerID JOIN `cities` ON customers.DeliveryCityID = cities.CityID JOIN `stateprovinces` ON cities.StateProvinceID = stateprovinces.StateProvinceID WHERE `PickingCompletedWhen` IS NULL AND orders.OrderID LIKE ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("CustomerID"), resultSet.getString("CustomerName"), resultSet.getString("CityName"), resultSet.getString("StateProvinceName"), resultSet.getString("DeliveryAddressLine1"), resultSet.getString("DeliveryAddressLine2"), resultSet.getString("DeliveryPostalCode"));
                this.orders.add(new Order(resultSet.getInt("OrderID"), customer, resultSet.getTimestamp("OrderDate"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
        for (Order o: orders){
            System.out.println(o.toString());
        }
        return orders;
    }
    public ArrayList<OrderLine> getOrderLines(int id){
        orderLines = new ArrayList<>();
        String sql = "SELECT * FROM `orderlines` JOIN `stockitems` ON orderlines.StockItemID = stockitems.StockItemID JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE OrderID LIKE ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Product product = new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand"));
                this.orderLines.add(new OrderLine(resultSet.getInt("OrderLineID"), product, resultSet.getInt("Quantity"), resultSet.getInt("pickedQuantity"), resultSet.getTimestamp("PickingCompletedWhen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
        for (OrderLine o: orderLines){
            System.out.println(o.toString());
        }
        return orderLines;
    }
}
