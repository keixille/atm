package com.example;

import java.util.HashMap;

public class ATMService {
    private HashMap<String, Account> accountTable = new HashMap<>();
    private HashMap<String, String> oweToTable = new HashMap<>();
    private HashMap<String, String> oweFromTable = new HashMap<>();
    private Account currentAccount = null;

    private boolean isCannotTransfer(String targetName) {
        if (!accountTable.containsKey(targetName)) {
            System.out.println("Transferred account not found!");
            return true;
        }

        if (currentAccount.getName().equals(targetName)) {
            System.out.println("Cannot transfer to same account!");
            return true;
        }

        if (currentAccount.getBalance() <= 0) {
            System.out.println("Cannot transfer balance less than or equal to 0!");
            return true;
        }
        return false;
    }

    public void login(String name) {
        if (!accountTable.containsKey(name)) {
            Account createdAccount = new Account(name);
            accountTable.put(name, createdAccount);
        }
        currentAccount = accountTable.get(name);

        System.out.println("Hello, " + currentAccount.getName() + "!");
        System.out.println("Your balance is $" + currentAccount.getBalance());

        if (currentAccount.getOwedBalance() > 0) {
            Account owedToAccount = accountTable.get(oweToTable.get(name));

            System.out.println("Owed $" + currentAccount.getOwedBalance() + " to " + owedToAccount.getName());
        } else {
            if (oweFromTable.containsKey(name)) {
                Account owedFromAccount = accountTable.get(oweFromTable.get(name));

                System.out.println("Owed $" + owedFromAccount.getOwedBalance() + " from " + owedFromAccount.getName());
            }
        }
    }

    public void deposit(int depositBalance) {
        if (currentAccount.getOwedBalance() > 0) {
            String targetName = oweToTable.get(currentAccount.getName());
            Account targetAccount = accountTable.get(targetName);

            int movingBalance = currentAccount.increaseBalance(depositBalance);
            targetAccount.increaseBalance(movingBalance);

            System.out.println("Transferred $" + movingBalance + " to " + targetAccount.getName());
            System.out.println("Your balance is $" + currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                System.out.println("Owed $" + currentAccount.getOwedBalance() + " to " + targetName);
            } else {
                oweToTable.remove(currentAccount.getName());
                oweFromTable.remove(targetName);
            }
        } else {
            currentAccount.increaseBalance(depositBalance);

            System.out.println("Your balance is $" + currentAccount.getBalance());
        }
    }

    public void withdraw(int withdrawBalance) {
        int deductedBalance = currentAccount.getBalance() - withdrawBalance;

        if (deductedBalance >= 0) {
            currentAccount.decreaseBalance(withdrawBalance);

            System.out.println("Your balance is $" + currentAccount.getBalance());
        } else {
            System.out.println("Insufficient balance to withdraw!");
        }
    }

    public void transfer(String targetName, int transferBalance) {
        if(isCannotTransfer(targetName)) return;

        if (oweFromTable.containsKey(currentAccount.getName())) {
            Account owedFromAccount = accountTable.get(oweFromTable.get(currentAccount.getName()));
            int movingBalance = owedFromAccount.increaseBalance(transferBalance);
            if(transferBalance > movingBalance) {
                currentAccount.decreaseBalance(transferBalance - movingBalance);
                System.out.println("Transferred $" + (transferBalance - movingBalance) + " to " + owedFromAccount.getName());
            }

            System.out.println("Your balance is $" + currentAccount.getBalance());
            if(owedFromAccount.getOwedBalance() > 0) {
                System.out.println("Owed $" + owedFromAccount.getOwedBalance() + " from " + owedFromAccount.getName());
            } else {
                oweToTable.remove(targetName);
                oweFromTable.remove(currentAccount.getName());
            }
        } else {
            Account targetAccount = accountTable.get(targetName);

            int movingBalance = currentAccount.decreaseBalance(transferBalance);
            targetAccount.increaseBalance(movingBalance);

            if (currentAccount.getOwedBalance() > 0) {
                oweToTable.put(currentAccount.getName(), targetAccount.getName());
                oweFromTable.put(targetAccount.getName(), currentAccount.getName());
            }

            System.out.println("Transferred $" + movingBalance + " to " + targetAccount.getName());
            System.out.println("Your balance is $" + currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                System.out.println("Owed $" + currentAccount.getOwedBalance() + " to " + targetAccount.getName());
            }
        }
    }

    public void logout() {
        System.out.println("Goodbye, " + currentAccount.getName() + "!");
        currentAccount = null;
    }
}
