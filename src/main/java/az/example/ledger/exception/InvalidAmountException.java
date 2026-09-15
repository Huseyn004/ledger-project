package az.example.ledger.exception;

public class InvalidAmountException extends LedgerException {
    public InvalidAmountException(String message) { super(message); }
}