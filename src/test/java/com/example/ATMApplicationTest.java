package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ATMApplicationTest {
    private static ATMApplication atmApplication;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        atmApplication = new ATMApplication();
    }

    @Test
    @DisplayName("Get name, balance, owed balance")
    void commandTest() {
        String inputLogin = "login Alice\n";
        String inputDeposit = "deposit 100\n";
        String inputInvalidDepositBalance = "deposit a\n";
        String inputWithdraw = "withdraw 50\n";
        String inputInvalidWithdrawBalance = "withdraw a\n";
        String inputTransfer = "transfer Bob 50\n";
        String inputInvalidTransferBalance = "transfer Bob a\n";
        String inputLogout = "logout\n";
        String inputInvalid = "invalid test\n";
        String inputExit = "exit\n";

        String inputStr = inputLogin + inputDeposit + inputInvalidDepositBalance;
        inputStr += inputWithdraw + inputInvalidWithdrawBalance;
        inputStr += inputTransfer + inputInvalidTransferBalance;
        inputStr += inputLogout + inputInvalid + inputExit;

        InputStream in = new ByteArrayInputStream(inputStr.getBytes());
        System.setIn(in);
        atmApplication.main(new String[]{"arg1", "arg2"});

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("```bash", split[0].trim());
        assertEquals("Hello, Alice!", split[1].trim());
        assertEquals("Your balance is $0", split[2].trim());
        assertEquals("Your balance is $100", split[3].trim());
        assertEquals("Input is not a number!", split[4].trim());
        assertEquals("Your balance is $50", split[5].trim());
        assertEquals("Input is not a number!", split[6].trim());
        assertEquals("Transferred account not found!", split[7].trim());
        assertEquals("Input is not a number!", split[8].trim());
        assertEquals("Goodbye, Alice!", split[9].trim());
        assertEquals("Unrecognized command!", split[10].trim());
        assertEquals("```", split[11].trim());
    }
}
