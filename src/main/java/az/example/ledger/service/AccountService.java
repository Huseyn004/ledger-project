package az.example.ledger.service;

import az.example.ledger.exception.AccountNotFoundException;
import az.example.ledger.exception.SameAccountTransferException;
import az.example.ledger.model.Account;
import az.example.ledger.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() { return accountRepository.findAll(); }

    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + number));
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = findByNumber(accountNumber);
        account.deposit(amount);
        accountRepository.save(account);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = findByNumber(accountNumber);
        account.withdraw(amount);
        accountRepository.save(account);
    }

    public void transfer(String fromNum, String toNum, BigDecimal amount) {
        if (fromNum.equalsIgnoreCase(toNum)) {
            throw new SameAccountTransferException("Cannot transfer to the same account.");
        }
        withdraw(fromNum, amount);
        deposit(toNum, amount);
    }
}