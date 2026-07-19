package model;

import java.sql.Date;

public class Transaction {

    private int accountNo;
    private String transactionType;
    private double amount;
    private double balance;
    private Date transactionDate;

    // Default Constructor
    public Transaction() {

    }

    // Parameterized Constructor
    public Transaction(int accountNo, String transactionType,
                       double amount, double balance,
                       Date transactionDate) {

        this.accountNo = accountNo;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.transactionDate = transactionDate;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}