package az.example.ledger.test;

import az.example.ledger.exception.MinimumBalanceException;
import az.example.ledger.model.SavingsAccount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {
    @Test
    void testMinimumBalanceConstraint() {
        SavingsAccount acc = new SavingsAccount("SAV1", new BigDecimal("100.00"));
        assertThrows(MinimumBalanceException.class, () -> acc.withdraw(new BigDecimal("60.00")));
    }
}