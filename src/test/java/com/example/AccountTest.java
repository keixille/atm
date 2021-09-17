package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private Account account;
    private static final String initialName = "Linda";

    @BeforeEach
    void setUp() {
        account = new Account(initialName);
    }

    @Test
    @DisplayName("Get name, balance, owed balance")
    void getInitTest() {
        final int balance = 0;
        final int owedBalance = 0;

        assertEquals(initialName, account.getName());
        assertEquals(balance, account.getBalance());
        assertEquals(owedBalance, account.getOwedBalance());
    }

    @Test
    @DisplayName("Increase balance")
    void normalIncreaseBalanceTest() {
        final int balance = 50;
        final int owedBalance = 0;

        account.increaseBalance(balance);

        assertEquals(balance, account.getBalance());
        assertEquals(owedBalance, account.getOwedBalance());
    }

    @Test
    @DisplayName("Decrease balance")
    void normalDecreaseBalanceTest() {
        final int increasedBalance = 50;
        final int decreasedBalance = 30;

        account.increaseBalance(increasedBalance);
        account.decreaseBalance(decreasedBalance);

        assertEquals(increasedBalance - decreasedBalance, account.getBalance());
    }

    @Test
    @DisplayName("Increase balance less than owed balance")
    void increaseBalanceLessThanOwedBalanceTest() {
        final int[] increasedBalance = {50, 10};
        final int decreasedBalance = 70;

        account.increaseBalance(increasedBalance[0]);
        account.decreaseBalance(decreasedBalance);
        account.increaseBalance(increasedBalance[1]);

        assertEquals(0, account.getBalance());
        assertEquals(decreasedBalance - increasedBalance[0] - increasedBalance[1], account.getOwedBalance());
    }

    @Test
    @DisplayName("Increase balance more than owed balance")
    void increaseBalanceMoreThanOwedBalanceTest() {
        final int[] increasedBalance = {50, 40};
        final int decreasedBalance = 70;

        account.increaseBalance(increasedBalance[0]);
        account.decreaseBalance(decreasedBalance);
        account.increaseBalance(increasedBalance[1]);

        assertEquals(20, account.getBalance());
        assertEquals(0, account.getOwedBalance());
    }

    @Test
    @DisplayName("Transfer balance when decrease balance more than init balance")
    void transferBalanceDecreaseMoreThanInitTest() {
        final int initBalance = 50;
        final int decreasedBalance = 70;

        account.increaseBalance(initBalance);
        int transferBalance = account.decreaseBalance(decreasedBalance);

        assertEquals(initBalance, transferBalance);
    }

    @Test
    @DisplayName("Transfer balance when decrease balance less than init balance")
    void transferBalanceDecreaseLessThanInitTest() {
        final int initBalance = 50;
        final int decreasedBalance = 20;

        account.increaseBalance(initBalance);
        int transferBalance = account.decreaseBalance(decreasedBalance);

        assertEquals(decreasedBalance, transferBalance);
    }

    @Test
    @DisplayName("Transfer balance when increase balance more than owed balance")
    void transferBalanceIncreaseMoreThanOwedTest() {
        final int initBalance = 20;
        final int decreasedBalance = 50;
        final int increasedBalance = 40;

        account.increaseBalance(initBalance);
        account.decreaseBalance(decreasedBalance);
        int transferBalance = account.increaseBalance(increasedBalance);

        assertEquals(decreasedBalance - initBalance, transferBalance);
    }

    @Test
    @DisplayName("Transfer balance when increase balance less than owed balance")
    void transferBalanceIncreaseLessThanOwedTest() {
        final int initBalance = 20;
        final int decreasedBalance = 50;
        final int increasedBalance = 20;

        account.increaseBalance(initBalance);
        account.decreaseBalance(decreasedBalance);
        int transferBalance = account.increaseBalance(increasedBalance);

        assertEquals(increasedBalance, transferBalance);
    }
}
