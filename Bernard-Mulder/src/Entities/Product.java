package Entities;

public class Product {
    private int id;
    private String name;
    private int stock;

    public Product(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " stock: " + stock;
    }
}
