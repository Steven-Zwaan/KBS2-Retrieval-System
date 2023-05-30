package Models;

import Database.DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderPacking {
    private int id;
    private String name;
    private int TypicalWeightPerUnit;
    private DatabaseConnector databaseConnector = new DatabaseConnector();


    public OrderPacking(int id, String name, int TypicalWeightPerUnit) {
        this.id = id;
        this.name = name;
        this.TypicalWeightPerUnit = TypicalWeightPerUnit;
    }
    public void getTypicalWeightPerUnit(){
        String sql = "SELECT TypicalWeightPerUnit FROM `stockitems` LEFT JOIN `stockitemholdings` ON stockitems.StockItemID = stockitemholdings.StockItemID";
        System.out.println(sql);

        try {
            PreparedStatement statement = databaseConnector.connect().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnector.disconnect();
        }
    }

}
