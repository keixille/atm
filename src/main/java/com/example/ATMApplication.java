package com.example;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ATMApplication {
    public static void main(String[] args) {
        ATMService atmService = new ATMService();
        Utils utils = new Utils();

        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        System.out.println("```bash");
        while (!isExit) {
            String rawInput = scanner.nextLine();
            String[] splitInput = rawInput.split("\\s+");

            switch (splitInput[0]) {
                case "login":
                    atmService.login(splitInput[1]);
                    break;
                case "deposit":
                    if (!utils.isNumber(splitInput[1])) break;

                    atmService.deposit(parseInt(splitInput[1]));
                    break;
                case "withdraw":
                    if (!utils.isNumber(splitInput[1])) break;

                    atmService.withdraw(parseInt(splitInput[1]));
                    break;
                case "transfer":
                    if (!utils.isNumber(splitInput[2])) break;

                    atmService.transfer(splitInput[1], parseInt(splitInput[2]));
                    break;
                case "logout":
                    atmService.logout();
                    break;
                case "exit":
                    isExit = true;
                    break;
                default:
                    System.out.println("Unrecognized command!");
            }
        }
        System.out.println("```");
    }
}
