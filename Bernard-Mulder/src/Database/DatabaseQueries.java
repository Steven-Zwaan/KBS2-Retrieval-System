package Database;

import Database.DatabaseConnector;
import Entities.Product;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseQueries {
    // !_ note _! this is just init
    // it will not create a connection
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private ArrayList<Product> products;
    //        String sql = "SELECT * FROM `orders`";
//        try {
//            PreparedStatement statement = database.connect().prepareStatement(sql);
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()){
//                System.out.println("OrderID: " + resultSet.getInt("OrderID") + " CustomerId: " + resultSet.getInt("CustomerID"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            database.disconnect();
//        }
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
        for (Product p: products){
            System.out.println(p.toString());
        }
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
        for (Product p: products){
            System.out.println(p.toString());
        }
        return products;
    }
}
