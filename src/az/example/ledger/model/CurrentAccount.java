package az.example.ledger.model;

import az.example.ledger.exception.InsufficientFundsException;
import az.example.ledger.exception.InvalidAmountException;
import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private static final BigDecimal OVERDRAFT_LIMIT = new BigDecimal("-500.00");

    public CurrentAccount(String accountNumber, BigDecimal initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero", amount);
        }
        BigDecimal prospectiveBalance = balance.subtract(amount);
        if (prospectiveBalance.compareTo(OVERDRAFT_LIMIT) < 0) {
            throw new InsufficientFundsException(getAccountNumber(), amount, balance.subtract(OVERDRAFT_LIMIT));
        }
        this.balance = prospectiveBalance;
        addTransaction(TransactionType.WITHDRAWAL, amount);
    }
}