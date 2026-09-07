package test;

import az.example.ledger.exception.AccountNotFoundException;
import az.example.ledger.exception.InsufficientFundsException;
import az.example.ledger.exception.SameAccountTransferException;
import az.example.ledger.model.CurrentAccount;
import az.example.ledger.model.SavingsAccount;
import az.example.ledger.repository.InMemoryAccountRepository;
import az.example.ledger.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService(new InMemoryAccountRepository());
        service.openAccount(new CurrentAccount("ACC-001", new BigDecimal("500.00")));
        service.openAccount(new SavingsAccount("ACC-002", new BigDecimal("200.00")));
    }

    @Test
    void testSuccessfulTransfer() {
        service.transfer("ACC-001", "ACC-002", new BigDecimal("100.00"));

        assertEquals(new BigDecimal("400.00"), service.getAccount("ACC-001").getBalance());
        assertEquals(new BigDecimal("300.00"), service.getAccount("ACC-002").getBalance());
    }

    @Test
    void testTransferToSameAccountThrowsException() {
        assertThrows(SameAccountTransferException.class, () ->
                service.transfer("ACC-001", "ACC-001", new BigDecimal("50.00"))
        );
    }

    @Test
    void testTransferFailsWhenSourceHasInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () ->
                service.transfer("ACC-001", "ACC-002", new BigDecimal("1100.00"))
        );

        assertEquals(new BigDecimal("500.00"), service.getAccount("ACC-001").getBalance());
        assertEquals(new BigDecimal("200.00"), service.getAccount("ACC-002").getBalance());
    }

    @Test
    void testGetNonExistentAccountThrowsException() {
        assertThrows(AccountNotFoundException.class, () ->
                service.getAccount("ACC-999")
        );
    }
}