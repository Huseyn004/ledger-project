package az.example.ledger.service;

import az.example.ledger.exception.AccountNotFoundException;
import az.example.ledger.exception.SameAccountTransferException;
import az.example.ledger.model.Account;
import az.example.ledger.model.Transaction;
import az.example.ledger.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void openAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(String fromAccountNum, String toAccountNum, BigDecimal amount) {
        if (fromAccountNum.equals(toAccountNum)) {
            throw new SameAccountTransferException(fromAccountNum);
        }

        Account source = getAccount(fromAccountNum);
        Account target = getAccount(toAccountNum);

        // Deduct from source account (logs TRANSFER_OUT)
        source.withdrawForTransfer(amount);

        try {
            // Credit target account (logs TRANSFER_IN)
            target.depositFromTransfer(amount);
        } catch (RuntimeException e) {
            // Compensating action: undo debit if deposit fails
            source.deposit(amount);
            throw e;
        }
    }

    public List<Transaction> getStatement(String accountNumber) {
        Account account = getAccount(accountNumber);
        return account.getTransactions();
    }
}