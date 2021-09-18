package com.example;

import java.util.HashMap;

public class ATMService {
    private HashMap<String, Account> accountTable = new HashMap<>();
    private HashMap<String, String> oweToTable = new HashMap<>();
    private HashMap<String, String> oweFromTable = new HashMap<>();
    private Account currentAccount = null;
    private Utils utils = new Utils();

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

        utils.messageHello(currentAccount.getName());
        utils.messageCurrentBalance(currentAccount.getBalance());

        if (currentAccount.getOwedBalance() > 0) {
            Account owedToAccount = accountTable.get(oweToTable.get(name));
            utils.messageOwedTo(currentAccount.getOwedBalance(), owedToAccount.getName());
        } else {
            if (oweFromTable.containsKey(name)) {
                Account owedFromAccount = accountTable.get(oweFromTable.get(name));
                utils.messageOwedFrom(owedFromAccount.getOwedBalance(), owedFromAccount.getName());
            }
        }
    }

    public void deposit(int depositBalance) {
        if (currentAccount.getOwedBalance() > 0) {
            String targetName = oweToTable.get(currentAccount.getName());
            Account targetAccount = accountTable.get(targetName);

            int movingBalance = currentAccount.increaseBalance(depositBalance);
            targetAccount.increaseBalance(movingBalance);

            utils.messageTransfer(movingBalance, targetAccount.getName());
            utils.messageCurrentBalance(currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                utils.messageOwedTo(currentAccount.getOwedBalance(), targetName);
            } else {
                oweToTable.remove(currentAccount.getName());
                oweFromTable.remove(targetName);
            }
        } else {
            currentAccount.increaseBalance(depositBalance);
            utils.messageCurrentBalance(currentAccount.getBalance());
        }
    }

    public void withdraw(int withdrawBalance) {
        int deductedBalance = currentAccount.getBalance() - withdrawBalance;

        if (deductedBalance >= 0) {
            currentAccount.decreaseBalance(withdrawBalance);
            utils.messageCurrentBalance(currentAccount.getBalance());
        } else {
            System.out.println("Insufficient balance to withdraw!");
        }
    }

    public void transfer(String targetName, int transferBalance) {
        if (isCannotTransfer(targetName)) return;

        if (oweFromTable.containsKey(currentAccount.getName())) {
            Account owedFromAccount = accountTable.get(oweFromTable.get(currentAccount.getName()));

            int movingBalance = owedFromAccount.increaseBalance(transferBalance);
            if (transferBalance > movingBalance) {
                currentAccount.decreaseBalance(transferBalance - movingBalance);
                utils.messageTransfer((transferBalance - movingBalance), owedFromAccount.getName());
            }

            utils.messageCurrentBalance(currentAccount.getBalance());
            if (owedFromAccount.getOwedBalance() > 0) {
                utils.messageOwedFrom(owedFromAccount.getOwedBalance(), owedFromAccount.getName());
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

            utils.messageTransfer(movingBalance, targetAccount.getName());
            utils.messageCurrentBalance(currentAccount.getBalance());
            if (currentAccount.getOwedBalance() > 0) {
                utils.messageOwedTo(currentAccount.getOwedBalance(), targetAccount.getName());
            }
        }
    }

    public void logout() {
        utils.messageGoodbye(currentAccount.getName());
        currentAccount = null;
    }
}