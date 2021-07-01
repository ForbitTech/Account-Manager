package com.forbitbd.accountmanager.model;

public class Transaction {

    private String date, price, name, product;

    public Transaction(String date, String price, String name, String product) {
        this.date = date;
        this.price = price;
        this.name = name;
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
