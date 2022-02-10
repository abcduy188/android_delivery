package com.example.model;

import androidx.annotation.NonNull;

public class Product {
    private int ProductID;
    private String ProductName;
    private double ProductPrice;

    public Product(int productID, String productName, double productPrice) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return ProductID+ ": " +ProductName+" >> "+ProductPrice;
    }
}
