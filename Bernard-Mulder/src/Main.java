import Database.*;
import Entities.Order;
import Entities.Orders;
import Entities.Product;
import Entities.Products;

public class Main {
    public static void main(String[] args) {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("COM3");

//        Products products = new Products();
//        products.storeProductsFromDatabase();
//        products.storeProductFromDatabase("USB");
//        for (Product p: products.getProducts()){
//            System.out.println(p.toString());
//        }

//        Orders orders = new Orders();
//        orders.storeOrdersFromDatabase();
//        orders.storeOrderFromDatabase(71668);
//        for (Order o: orders.getOrders()){
//            System.out.println(o.toString());
//            o.getOrderLines();
//        }
    }
}