package com.example.model;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String title;
    private byte[] pic;
    private String description;
    private Double fee;
    private int idCate;
    private int numberInCart;

    public Food(int id,String title, byte[] pic, String description, Double fee,int idCate) {
        this.idCate = idCate;
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
    }

    public Food(String title, byte[] pic, String description, Double fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    
}
