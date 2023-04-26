package Entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Order {
    private int id;
    private Customer customer;
    private Timestamp date;
    private Timestamp pickingCompletedWhen;

//    public Order(int id, Customer customer, Timestamp date) {
//        this.id = id;
//        this.customer = customer;
//        this.date = date;
//        this.pickingCompletedWhen = null;
//    }
    public Order(int id, Customer customer, Timestamp date, Timestamp pickingCompletedWhen) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Timestamp getDate() {
        return date;
    }

    public Timestamp getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    @Override
    public String toString() {
        return "id: " + id + " order date: " + date + " picked when: " + pickingCompletedWhen + " " + customer;
    }
}
