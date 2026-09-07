package az.example.ledger.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends LedgerException {
    private final String accountNumber;
    private final BigDecimal requested;
    private final BigDecimal available;

    public InsufficientFundsException(String accountNumber, BigDecimal requested, BigDecimal available) {
        super(String.format("Account %s: requested %s but only %s available", accountNumber, requested, available));
        this.accountNumber = accountNumber;
        this.requested = requested;
        this.available = available;
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getRequested() { return requested; }
    public BigDecimal getAvailable() { return available; }
}