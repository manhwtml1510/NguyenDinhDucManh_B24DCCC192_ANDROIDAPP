package com.example.myappktgk.model;
public class Ebook extends Document {
    private double fileSize;

    public Ebook(String id, String name, double price, double fileSize) {
        super(id, name, price);
        setFileSize(fileSize);
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        if (fileSize <= 0) {
            throw new IllegalArgumentException("Dung lượng phải > 0");
        }
        this.fileSize = fileSize;
    }

    @Override
    public double tinhPhiMuon() {
        return fileSize * 1000;
    }
}