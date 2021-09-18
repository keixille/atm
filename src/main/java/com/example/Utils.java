package com.example;

import static java.lang.Integer.parseInt;

public class Utils {
    public void messageHello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public void messageCurrentBalance(int balance) {
        System.out.println("Your balance is $" + balance);
    }

    public void messageTransfer(int balance, String name) {
        System.out.println("Transferred $" + balance + " to " + name);
    }

    public void messageOwedTo(int balance, String name) {
        System.out.println("Owed $" + balance + " to " + name);
    }

    public void messageOwedFrom(int balance, String name) {
        System.out.println("Owed $" + balance + " from " + name);
    }

    public void messageGoodbye(String name) {
        System.out.println("Goodbye, " + name + "!");
    }

    public boolean isNumber(String input) {
        try {
            parseInt(input);
        } catch (Exception e) {
            System.out.println("Input is not a number!");
            return false;
        }
        return true;
    }
}
