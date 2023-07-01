package com.example.easylpg;

public class OrderItem {
    private int cust_id;
    private int quantity;
    private String name;
    private double totalcost;

    public OrderItem(int cust_id, int quantity, String name, double totalcost) {
        this.cust_id = cust_id;
        this.quantity = quantity;
        this.name = name;
        this.totalcost = totalcost;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }
}
