import Database.DatabaseConnector;
import Database.DatabaseQueries;

public class Main {
    public static void main(String[] args) {
    DatabaseQueries databaseQueries = new DatabaseQueries();
//        databaseQueries.getProducts();
        databaseQueries.getProduct(77);
    }
}