package Entities;

public class Customer {
    private int id;
    private int name;
    private String city;
    private String state;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;

    public Customer(int id, int name, String city, String state, String addressLine1, String addressLine2, String postalCode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public int getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
