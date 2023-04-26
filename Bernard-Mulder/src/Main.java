import Database.Database;
import Entities.Product;

public class Main {
    public static void main(String[] args) {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("COM3");
    Database database = new Database();
    database.getOrderLines(32616);
//        System.out.println(database.getOrders());

//        databaseQueries.getProducts();
//        database.getProduct(77);
//        database.getProduct("USB");
    }
}