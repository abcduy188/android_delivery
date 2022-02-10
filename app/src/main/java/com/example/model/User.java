package com.example.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String Name;
    private String Sdt;
    private String DiaChi;

    public User(int id, String username, String password, String name, String sdt, String diaChi) {
        this.id = id;
        this.username = username;
        this.password = password;
        Name = name;
        Sdt = sdt;
        DiaChi = diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
