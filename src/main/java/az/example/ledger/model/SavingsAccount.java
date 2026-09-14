package az.example.ledger.model;

import az.example.ledger.exception.InvalidAmountException;
import az.example.ledger.exception.MinimumBalanceException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("50.00");

    public SavingsAccount(String accountNumber, BigDecimal initialBalance) {
        super(accountNumber, initialBalance);
        if (initialBalance.compareTo(MINIMUM_BALANCE) < 0) {
            throw new InvalidAmountException("Opening balance must be at least 50.00 for SavingsAccount", initialBalance);
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero", amount);
        }
        BigDecimal prospectiveBalance = balance.subtract(amount);
        if (prospectiveBalance.compareTo(MINIMUM_BALANCE) < 0) {
            throw new MinimumBalanceException(getAccountNumber(), amount, MINIMUM_BALANCE);
        }
        this.balance = prospectiveBalance;
    }

    @Override
    public BigDecimal calculateMonthlyInterest() {
        return balance.multiply(new BigDecimal("0.05"))
                .divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP);
    }
}