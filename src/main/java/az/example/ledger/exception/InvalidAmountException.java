package az.example.ledger.exception;

import java.math.BigDecimal;

public class InvalidAmountException extends LedgerException {
    private final BigDecimal amount;

    public InvalidAmountException(String message, BigDecimal amount) {
        super(message + " (Amount: " + amount + ")");
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}