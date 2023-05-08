import Entities.*;

public class Main {
    public static void main(String[] args) {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("COM3");

//        Products products = new Products();
//        products.storeProductsFromDatabase();
//        products.storeProductFromDatabase(98);
//        for (Product p: products.getProducts()){
//            p.setStock(30);
//        }
//        products.storeProductFromDatabase(198);
//        for (Product p: products.getProducts()){
//            System.out.println(p.toString());
//        }
//        products.storeProductFromDatabase(98);
//        for (Product p: products.getProducts()){
//            System.out.println(p.toString());
//            p.setStock(25);
//        }
//        products.storeProductFromDatabase(98);
//        for (Product p: products.getProducts()){
//            System.out.println(p.toString());
//        }

        Orders orders = new Orders();
        orders.getOrdersFromDatabase();
        orders.getOrderFromDatabase(694);
        for (Order o: orders.getOrders()){
            System.out.println(o.toString());
            for (OrderLine ol: o.getOrderLines()){
                System.out.println(ol.toString());
            }
            System.out.println(o.toString());
        }
    }
}