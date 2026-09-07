package test;

import az.example.ledger.exception.InvalidAmountException;
import az.example.ledger.exception.MinimumBalanceException;
import az.example.ledger.model.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {

    private SavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount("ACC-SAV-001", new BigDecimal("200.00"));
    }

    @Test
    void testOpeningBalanceBelowMinimumThrowsException() {
        assertThrows(InvalidAmountException.class, () ->
                new SavingsAccount("ACC-SAV-002", new BigDecimal("49.99"))
        );
    }

    @Test
    void testWithdrawalViolatingMinimumBalanceThrowsException() {
        assertThrows(MinimumBalanceException.class, () ->
                account.withdraw(new BigDecimal("160.00"))
        );
    }

    @Test
    void testValidWithdrawalPreservesMinimumBalance() {
        account.withdraw(new BigDecimal("150.00"));
        assertEquals(new BigDecimal("50.00"), account.getBalance());
    }

    @Test
    void testMonthlyInterestCalculation() {
        BigDecimal interest = account.calculateMonthlyInterest();
        assertEquals(new BigDecimal("0.83"), interest);
    }
}