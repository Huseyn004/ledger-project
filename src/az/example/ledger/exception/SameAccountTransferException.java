package az.example.ledger.exception;

public class SameAccountTransferException extends LedgerException {
    private final String accountNumber;

    public SameAccountTransferException(String accountNumber) {
        super("Cannot transfer to the same account: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() { return accountNumber; }
}