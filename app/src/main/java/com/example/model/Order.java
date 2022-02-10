package com.example.model;

import java.util.Date;

public class Order {

    private  int id ;
    private int UserID;
    private String CreateDate;
    private String ShipName;

    private int Status;
    private String ShipPhone;
    private double ProductPrice;
    private String ShipAddress;
    private int CountItems;

    public int getCountItems() {
        return CountItems;
    }

    public void setCountItems(int countItems) {
        CountItems = countItems;
    }

    public Order(int id, int userID, String createDate, String shipName, int status, String shipPhone, double productPrice, String shipAddress, int countItems) {
        this.id = id;
        UserID = userID;
        CreateDate = createDate;
        ShipName = shipName;

        Status = status;
        ShipPhone = shipPhone;
        ProductPrice = productPrice;
        ShipAddress = shipAddress;
        CountItems = countItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public String getShipPhone() {
        return ShipPhone;
    }

    public void setShipPhone(String shipPhone) {
        ShipPhone = shipPhone;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
    }
}
