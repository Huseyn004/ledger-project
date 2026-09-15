package az.example.ledger.test;

import az.example.ledger.model.CurrentAccount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {
    @Test
    void testDeposit() {
        CurrentAccount acc = new CurrentAccount("ACC1", new BigDecimal("100.00"));
        acc.deposit(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), acc.getBalance());
    }
}