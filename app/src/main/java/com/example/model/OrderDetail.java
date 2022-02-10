package com.example.model;

public class OrderDetail {
    private int id;
    private int idProduct;
    private int qty;
    private double price;
    private String nameProduct;

    public OrderDetail(int id, int idProduct, int qty, double price, String nameProduct) {
        this.id = id;
        this.idProduct = idProduct;
        this.qty = qty;
        this.price = price;
        this.nameProduct = nameProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
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
}
