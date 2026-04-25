package com.example.myappktgk.model;
public abstract class Document {
    private String id;
    private String name;
    private double price;

    public Document(String id, String name, double price) {
        this.id = id;
        setName(name);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không hợp lệ");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Giá phải > 0");
        }
        this.price = price;
    }

    public abstract double tinhPhiMuon();

    public String hienThiThongTin() {
        return "ID: " + id + "\nTên: " + name + "\nGiá: " + price;
    }
}