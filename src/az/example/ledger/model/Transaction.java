package az.example.ledger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private final String accountNumber;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public Transaction(String id, String accountNumber, TransactionType type, BigDecimal amount) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', type=" + type + ", amount=" + amount + ", date=" + createdAt + "}";
    }
}