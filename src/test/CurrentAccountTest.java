package test;

import az.example.ledger.exception.InsufficientFundsException;
import az.example.ledger.exception.InvalidAmountException;
import az.example.ledger.model.CurrentAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest {

    private CurrentAccount account;

    @BeforeEach
    void setUp() {
        account = new CurrentAccount("ACC-CURR-001", new BigDecimal("100.00"));
    }

    @Test
    void testDepositSuccess() {
        account.deposit(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    void testWithdrawWithinBalance() {
        account.withdraw(new BigDecimal("40.00"));
        assertEquals(new BigDecimal("60.00"), account.getBalance());
    }

    @Test
    void testWithdrawWithinOverdraftLimit() {
        account.withdraw(new BigDecimal("500.00"));
        assertEquals(new BigDecimal("-400.00"), account.getBalance());
    }

    @Test
    void testWithdrawExceedingOverdraftThrowsException() {
        assertThrows(InsufficientFundsException.class, () ->
                account.withdraw(new BigDecimal("700.00"))
        );
    }

    @Test
    void testNegativeDepositThrowsException() {
        assertThrows(InvalidAmountException.class, () ->
                account.deposit(new BigDecimal("-10.00"))
        );
    }
}