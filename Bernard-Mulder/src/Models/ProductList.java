package Models;

import Database.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductList {
    private ArrayList<Product> productList = new ArrayList<>();
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public void getProductsFromDatabase(){
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            this.productList.clear();
            while(resultSet.next()){
                this.productList.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }
    public void getProductFromDatabase(int id){
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE stockitems.StockItemID = ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            this.productList.clear();
            while(resultSet.next()){
                this.productList.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }
    public void getProductFromDatabase(String name) {
        String sql = "SELECT * FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID WHERE stockitems.StockItemName LIKE ? ";
        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            this.productList.clear();
            while (resultSet.next()) {
                this.productList.add(new Product(resultSet.getInt("StockItemID"), resultSet.getString("StockItemName"), resultSet.getInt("QuantityOnHand")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}
