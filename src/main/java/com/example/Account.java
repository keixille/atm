package com.example;

public class Account {
    private String name;
    private int balance;
    private int owedBalance;

    Account(String name) {
        this.name = name;
        this.balance = 0;
        this.owedBalance = 0;
    }

    public String getName() {
        return this.name;
    }

    public int increaseBalance(int increasedBalance) {
        if (this.owedBalance > 0) {
            if (this.owedBalance < increasedBalance) {
                this.balance = increasedBalance - this.owedBalance;
                this.owedBalance = 0;
                return increasedBalance - this.balance;
            } else {
                this.owedBalance -= increasedBalance;
            }
        } else {
            this.balance += increasedBalance;
        }
        return increasedBalance;
    }

    public int decreaseBalance(int decreasedBalance) {
        if (this.balance < decreasedBalance) {
            this.owedBalance = decreasedBalance - this.balance;
            this.balance = 0;
            return decreasedBalance - this.owedBalance;
        }

        this.balance -= decreasedBalance;
        return decreasedBalance;
    }

    public int getBalance() {
        return this.balance;
    }

    public int getOwedBalance() {
        return this.owedBalance;
    }
}
