import Database.Database;

public class Main {
    public static void main(String[] args) {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("COM3");
    Database database = new Database();
//        databaseQueries.getProducts();
        database.getProduct(77);
        database.getProduct("USB");
    }
}