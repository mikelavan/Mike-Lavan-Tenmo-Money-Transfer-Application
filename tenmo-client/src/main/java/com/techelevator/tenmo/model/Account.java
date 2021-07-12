package com.techelevator.tenmo.model;

public class Account {
    private int accountId;
    private int userId;
    private double balance;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString() {
        return "\n--------------------------------------------" +
                "\n Account Details" +
                "\n--------------------------------------------" +
                "\n AccountId: " + accountId +
                "\n UserId: " + userId + '\'' +
                "\n Balance: " + balance;
    }
}
