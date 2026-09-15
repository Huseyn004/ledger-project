package az.example.ledger.console;

import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public char[] readPassword(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().toCharArray();
    }
}