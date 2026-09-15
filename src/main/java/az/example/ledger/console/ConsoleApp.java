package az.example.ledger.console;

import az.example.ledger.security.Session;
import az.example.ledger.service.AccountService;
import az.example.ledger.util.MoneyFormatter;

import java.math.BigDecimal;

public class ConsoleApp {
    private final Session session;
    private final AccountService accountService;
    private final ConsolePrinter printer = new ConsolePrinter();

    public ConsoleApp(Session session, AccountService accountService) {
        this.session = session;
        this.accountService = accountService;
    }

    public void run(ConsoleReader reader) {
        printer.print("Welcome " + session.username() + "!");
        boolean running = true;

        while (running) {
            printer.print("\n--- MENU ---");
            printer.print("1. View All Accounts");
            printer.print("2. Deposit");
            printer.print("3. Withdraw");
            printer.print("4. Transfer");
            printer.print("5. Exit");

            String choice = reader.readLine("Choice");
            try {
                switch (choice) {
                    case "1" -> accountService.findAll().forEach(a ->
                            printer.print(a.getAccountNumber() + " [" + a.getType() + "]: " + MoneyFormatter.format(a.getBalance())));
                    case "2" -> {
                        String acc = reader.readLine("Account Number");
                        BigDecimal amt = new BigDecimal(reader.readLine("Amount"));
                        accountService.deposit(acc, amt);
                        printer.print("Deposit successful.");
                    }
                    case "3" -> {
                        String acc = reader.readLine("Account Number");
                        BigDecimal amt = new BigDecimal(reader.readLine("Amount"));
                        accountService.withdraw(acc, amt);
                        printer.print("Withdrawal successful.");
                    }
                    case "4" -> {
                        String from = reader.readLine("From Account");
                        String to = reader.readLine("To Account");
                        BigDecimal amt = new BigDecimal(reader.readLine("Amount"));
                        accountService.transfer(from, to, amt);
                        printer.print("Transfer successful.");
                    }
                    case "5" -> running = false;
                    default -> printer.print("Invalid choice.");
                }
            } catch (Exception e) {
                printer.print("Error: " + e.getMessage());
            }
        }
    }
}