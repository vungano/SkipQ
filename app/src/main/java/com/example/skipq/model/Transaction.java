package com.example.skipq.model;

public class Transaction {
    private Boolean transactionVerified;    // Status of transaction
    private String transactionTime;         // Time of transaction
    private Double transactionAmount;       //Amount of transaction

    public Transaction() {
    }

    public Transaction(Boolean transactionVerified, String transactionTime, Double transactionAmount) {
        this.transactionVerified = transactionVerified;
        this.transactionTime = transactionTime;
        this.transactionAmount = transactionAmount;
    }

    public Boolean getTransactionVerified() {
        return transactionVerified;
    }

    public void setTransactionVerified(Boolean transactionVerified) {
        this.transactionVerified = transactionVerified;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
