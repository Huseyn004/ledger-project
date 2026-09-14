package az.example.ledger.exception;

import java.math.BigDecimal;

public class MinimumBalanceException extends LedgerException {
    private final String accountNumber;
    private final BigDecimal requested;
    private final BigDecimal minimumRequired;

    public MinimumBalanceException(String accountNumber, BigDecimal requested, BigDecimal minimumRequired) {
        super(String.format("Account %s: withdrawal of %s violates minimum balance requirement of %s",
                accountNumber, requested, minimumRequired));
        this.accountNumber = accountNumber;
        this.requested = requested;
        this.minimumRequired = minimumRequired;
    }

    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getRequested() { return requested; }
    public BigDecimal getMinimumRequired() { return minimumRequired; }
}