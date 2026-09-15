package az.example.ledger.exception;

public class SameAccountTransferException extends LedgerException {
    public SameAccountTransferException(String message) { super(message); }
}