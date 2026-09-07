package az.example.ledger.exception;

public abstract class LedgerException extends RuntimeException {
    protected LedgerException(String message) {
        super(message);
    }

    protected LedgerException(String message, Throwable cause) {
        super(message, cause);
    }
}