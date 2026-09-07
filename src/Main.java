import az.example.ledger.exception.LedgerException;
import az.example.ledger.model.*;
import az.example.ledger.repository.InMemoryAccountRepository;
import az.example.ledger.service.AccountService;
import az.example.ledger.service.ReportService;
import az.example.ledger.util.MoneyFormatter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        InMemoryAccountRepository repository = new InMemoryAccountRepository();
        AccountService accountService = new AccountService(repository);
        ReportService reportService = new ReportService(repository);

        Customer customer = new Customer("CUST-001", "Huseyn", "Rustamov");
        reportService.registerCustomer(customer);

        CurrentAccount acc1 = new CurrentAccount("ACC-001", new BigDecimal("1000.00"));
        SavingsAccount acc2 = new SavingsAccount("ACC-002", new BigDecimal("500.00"));

        accountService.openAccount(acc1);
        accountService.openAccount(acc2);

        customer.addAccount(acc1);
        customer.addAccount(acc2);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Select choice: ");
                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1" -> handleDeposit(scanner, accountService);
                    case "2" -> handleWithdraw(scanner, accountService);
                    case "3" -> handleTransfer(scanner, accountService);
                    case "4" -> handleStatement(scanner, accountService);
                    case "5" -> handleReports(reportService, customer.getId());
                    case "0" -> {
                        running = false;
                        System.out.println("Thank you for using Banking Ledger!");
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==================================");
        System.out.println("      BANKING LEDGER SYSTEM   ");
        System.out.println("==================================");
        System.out.println("1. Deposit Money");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Transfer Money");
        System.out.println("4. Print Formatted Statement");
        System.out.println("5. Print Customer Reports");
        System.out.println("0. Exit");
        System.out.println("----------------------------------");
    }

    private static void handleDeposit(Scanner scanner, AccountService accountService) {
        try {
            System.out.print("Account Number: ");
            String acc = scanner.nextLine();
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());
            accountService.deposit(acc, amount);
            System.out.println("Deposit successful!");
        } catch (LedgerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric format.");
        }
    }

    private static void handleWithdraw(Scanner scanner, AccountService accountService) {
        try {
            System.out.print("Account Number: ");
            String acc = scanner.nextLine();
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());
            accountService.withdraw(acc, amount);
            System.out.println("Withdrawal successful!");
        } catch (LedgerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric format.");
        }
    }

    private static void handleTransfer(Scanner scanner, AccountService accountService) {
        try {
            System.out.print("Source Account Number: ");
            String from = scanner.nextLine();
            System.out.print("Target Account Number: ");
            String to = scanner.nextLine();
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            accountService.transfer(from, to, amount);
            System.out.println("Transfer successful!");
        } catch (LedgerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric format.");
        }
    }

    private static void handleStatement(Scanner scanner, AccountService accountService) {
        try {
            System.out.print("Account Number: ");
            String acc = scanner.nextLine();
            Account account = accountService.getAccount(acc);
            List<Transaction> history = accountService.getStatement(acc);

            System.out.println("\n=== ACCOUNT STATEMENT FOR " + acc + " ===");
            System.out.printf("%-12s %-15s %16s %20s%n", "ID", "TYPE", "AMOUNT", "DATE");
            System.out.println("------------------------------------------------------------------");
            for (Transaction tx : history) {
                System.out.printf("%-12s %-15s %16s %20s%n",
                        tx.getId(),
                        tx.getType(),
                        MoneyFormatter.format(tx.getAmount()),
                        DATE_FORMAT.format(tx.getCreatedAt()));
            }
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-28s %16s%n", "BALANCE", MoneyFormatter.format(account.getBalance()));
        } catch (LedgerException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleReports(ReportService reportService, String customerId) {
        System.out.println("\n--- REPORTS ---");
        System.out.println("Total Balance for Customer " + customerId + ": " +
                MoneyFormatter.format(reportService.getTotalBalanceForCustomer(customerId)));

        System.out.println("\nTop Accounts By Balance:");
        for (Account acc : reportService.getTopAccountsByBalance(5)) {
            System.out.printf("Account: %-15s Balance: %s%n", acc.getAccountNumber(), MoneyFormatter.format(acc.getBalance()));
        }
    }
}