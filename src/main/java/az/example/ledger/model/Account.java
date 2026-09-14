package az.example.ledger.model;

import az.example.ledger.exception.InvalidAmountException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Account {
    private final String accountNumber;
    protected BigDecimal balance;
    private final List<Transaction> transactions = new ArrayList<>();
    private int transactionCounter = 1;

    public Account(String accountNumber, BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative or null", initialBalance);
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;

        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            addTransaction(TransactionType.DEPOSIT, initialBalance);
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    protected void addTransaction(TransactionType type, BigDecimal amount) {
        String txId = String.format("TX-%04d", transactionCounter++);
        transactions.add(new Transaction(txId, accountNumber, type, amount));
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero", amount);
        }
        this.balance = this.balance.add(amount);
        addTransaction(TransactionType.DEPOSIT, amount);
    }

    public void depositFromTransfer(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero", amount);
        }
        this.balance = this.balance.add(amount);
        addTransaction(TransactionType.TRANSFER_IN, amount);
    }

    public abstract void withdraw(BigDecimal amount);

    public void withdrawForTransfer(BigDecimal amount) {
        withdraw(amount);
        // Replace the last logged transaction (WITHDRAWAL) with TRANSFER_OUT
        if (!transactions.isEmpty()) {
            Transaction last = transactions.get(transactions.size() - 1);
            transactions.set(transactions.size() - 1,
                    new Transaction(last.getId(), accountNumber, TransactionType.TRANSFER_OUT, amount));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{number='" + accountNumber + "', balance=" + balance + "}";
    }
}