package com.example.myappktgk.model;
public class Magazine extends Document {
    private int issueNumber;

    public Magazine(String id, String name, double price, int issueNumber) {
        super(id, name, price);
        setIssueNumber(issueNumber);
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Số kỳ phải > 0");
        }
        this.issueNumber = issueNumber;
    }

    @Override
    public double tinhPhiMuon() {
        return getPrice() * 0.03;
    }
}