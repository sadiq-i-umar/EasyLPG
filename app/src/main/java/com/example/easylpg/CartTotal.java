package com.example.easylpg;

public class CartTotal {
    private int cust_id;
    private String order_status;
    private double total_cost;

    public CartTotal(int cust_id, String order_status, double total_cost) {
        this.cust_id = cust_id;
        this.order_status = order_status;
        this.total_cost = total_cost;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
}
