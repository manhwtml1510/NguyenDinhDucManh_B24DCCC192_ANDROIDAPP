package com.example.myappktgk.model;
public class Book extends Document {
    private int pages;

    public Book(String id, String name, double price, int pages) {
        super(id, name, price);
        setPages(pages);
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Số trang phải > 0");
        }
        this.pages = pages;
    }

    @Override
    public double tinhPhiMuon() {
        return getPrice() * 0.05;
    }
}