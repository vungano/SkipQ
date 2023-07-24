package com.example.skipq.model;

public class DetailedTransaction {
    private int transactionId;   //Transaction id of the product
    private String productName;  // Name of product
    private double productPrice; //Price of product
    private int quantity;        //Quantity of that particular product
    private double totalPrice;  //Total price i.e quantity * productPrice

    public DetailedTransaction() {
    }

    public DetailedTransaction(int transactionId, String productName, double productPrice, int quantity, double totalPrice) {
        this.transactionId = transactionId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
