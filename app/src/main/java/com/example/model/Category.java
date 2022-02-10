package com.example.model;

public class Category {
    private int id;
    private String title;
    private byte[] pic;

    public Category(int id, String title, byte[] pic) {
        this.id = id;
        this.title = title;
        this.pic = pic;
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
}
