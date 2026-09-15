package az.example.ledger.exception;
public class AccountNotFoundException extends LedgerException {
    public AccountNotFoundException(String message) { super(message); }
}