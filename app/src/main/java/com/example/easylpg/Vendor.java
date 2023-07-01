package com.example.easylpg;

public class Vendor {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double rating;
    private String vendorLogo;

    public Vendor(int id, String name, String email, String phone, String address, Double rating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.rating = rating;
    }

    public Vendor() {

    }

    public Vendor(int id, String name, String logo, Double rating) {
        this.id = id;
        this.name = name;
        this.vendorLogo = logo;
        this.rating = rating;
    }

    public Vendor(String name, String email, String logo) {
        this.name = name;
        this.email = email;
        this.vendorLogo = logo;
    }

    public Vendor(String name, String vendorLogo) {
        this.name = name;
        this.vendorLogo = vendorLogo;
    }

    public Vendor(String name, String vendorlogo, Double rating) {
        this.name = name;
        this.vendorLogo = vendorlogo;
        this.rating = rating;
    }

    public Vendor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getVendorLogo() {
        return vendorLogo;
    }

    public void setVendorLogo(String vendorLogo) {
        this.vendorLogo = vendorLogo;
    }
}
