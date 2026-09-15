package az.example.ledger.exception;

public class DataAccessException extends LedgerException {
    public DataAccessException(String message) { super(message); }
    public DataAccessException(String message, Throwable cause) { super(message, cause); }
}