package az.example.ledger;

import az.example.ledger.config.DbConfig;
import az.example.ledger.console.ConsoleApp;
import az.example.ledger.console.ConsoleReader;
import az.example.ledger.db.ConnectionFactory;
import az.example.ledger.db.DbInitializer;
import az.example.ledger.repository.*;
import az.example.ledger.security.LoginService;
import az.example.ledger.security.Session;
import az.example.ledger.service.AccountService;

public class Main {
    public static void main(String[] args) {
        DbConfig config = new DbConfig();
        ConnectionFactory connectionFactory = new ConnectionFactory(config);

        new DbInitializer(connectionFactory).init();

        AccountRepository accountRepository = new JdbcAccountRepository(connectionFactory);
        UserRepository userRepository = new JdbcUserRepository(connectionFactory);

        LoginService loginService = new LoginService(userRepository);
        AccountService accountService = new AccountService(accountRepository);
        ConsoleReader reader = new ConsoleReader();
        Session session = null;
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("=== LOGIN ===");
            String username = reader.readLine("Username");
            char[] password = reader.readPassword("Password");


            try {
                session = loginService.login(username, password);
                System.out.println("Login successful.");
                break;
            } catch (Exception e) {
                attempts++;
                System.out.println("Invalid username or password.");
            }
        }

        if (session == null) {
            System.out.println("Too many failed attempts. Exiting.");
            System.exit(1);
        }

        new ConsoleApp(session, accountService).run(reader);
    }
}