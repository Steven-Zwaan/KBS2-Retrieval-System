import Database.DatabaseConnector;
import Database.DatabaseQueries;

public class Main {
    public static void main(String[] args) {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("COM3");
    DatabaseQueries databaseQueries = new DatabaseQueries();
//        databaseQueries.getProducts();
        databaseQueries.getProduct(77);
        databaseQueries.getProduct("USB");
    }
}