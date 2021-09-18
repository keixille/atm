package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    private final Utils utils = new Utils();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Verify message hello")
    void messageHelloTest() {
        final String name = "Linda";
        utils.messageHello(name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Hello, " + name + "!", split[0].trim());
    }

    @Test
    @DisplayName("Verify message current balance")
    public void messageCurrentBalanceTest() {
        final int balance = 50;
        utils.messageCurrentBalance(balance);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Your balance is $" + balance, split[0].trim());
    }

    @Test
    @DisplayName("Verify message transfer")
    public void messageTransferTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageTransfer(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Transferred $" + balance + " to " + name, split[0].trim());
    }

    @Test
    @DisplayName("Verify message owed to")
    public void messageOwedToTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageOwedTo(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Owed $" + balance + " to " + name, split[0].trim());
    }

    @Test
    @DisplayName("Verify message owed from")
    public void messageOwedFromTest() {
        final int balance = 50;
        final String name = "Linda";
        utils.messageOwedFrom(balance, name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Owed $" + balance + " from " + name, split[0].trim());
    }

    @Test
    @DisplayName("Verify message goodbye")
    public void messageGoodbyeTest() {
        final String name = "Linda";
        utils.messageGoodbye(name);

        String[] split = outputStreamCaptor.toString().split("\n");
        assertEquals("Goodbye, " + name + "!", split[0].trim());
    }

    @Test
    @DisplayName("Verify valid number")
    public void isValidNumberTest() {
        final String num = "5";

        assertTrue(utils.isNumber(num));
    }

    @Test
    @DisplayName("Verify invalid number")
    public void isInvalidNumberTest() {
        final String num = "a";

        assertFalse(utils.isNumber(num));
    }
}
