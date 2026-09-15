package az.example.ledger.model;

import az.example.ledger.exception.MinimumBalanceException;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal MIN_BALANCE = new BigDecimal("50.00");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.03");

    public SavingsAccount(String accountNumber, BigDecimal balance) {
        super(accountNumber, balance);
    }

    @Override
    public String getType() { return "SAVINGS"; }

    @Override
    public void withdraw(BigDecimal amount) {
        if (this.balance.subtract(amount).compareTo(MIN_BALANCE) < 0) {
            throw new MinimumBalanceException("Savings account balance cannot fall below " + MIN_BALANCE);
        }
        super.withdraw(amount);
    }

    @Override
    public BigDecimal calculateInterest() {
        return this.balance.multiply(INTEREST_RATE);
    }
}