package org.jsp.atm;

import java.sql.Timestamp;

public class Transaction {
    private long accountNumber;
    private String transactionType;
    private double amount;
    private Timestamp timestamp;

    public Transaction(long accountNumber, String transactionType, double amount, Timestamp timestamp) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void displayTransaction() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + timestamp);
        System.out.println("==================================");
    }
}
