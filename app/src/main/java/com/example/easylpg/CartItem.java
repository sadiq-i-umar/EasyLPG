package com.example.easylpg;

public class CartItem {

    private int item_id;
    private int cust_id;
    private String name;
    private int cylinder_id;
    private int order_id;
    private int qty;
    private double price;
    private double total_cost;

    public CartItem(int item_id, int cust_id, String name, double price, int qty, double total_cost) {
        this.item_id = item_id;
        this.cust_id = cust_id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.total_cost = total_cost;
    }

    public CartItem(int cust_id, String name, double price, int qty, double total_cost) {
        this.cust_id = cust_id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.total_cost = total_cost;
    }

    public CartItem(int qty, String name, Double total_cost) {
        this.qty = qty;
        this.name = name;
        this.total_cost = total_cost;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCylinder_id() {
        return cylinder_id;
    }

    public void setCylinder_id(int cylinder_id) {
        this.cylinder_id = cylinder_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
}