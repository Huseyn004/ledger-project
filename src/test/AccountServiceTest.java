package az.example.ledger.test;

import az.example.ledger.model.CurrentAccount;
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
        InMemoryAccountRepository repo = new InMemoryAccountRepository();
        repo.save(new CurrentAccount("ACC1", new BigDecimal("100.00")));
        repo.save(new CurrentAccount("ACC2", new BigDecimal("50.00")));
        service = new AccountService(repo);
    }

    @Test
    void testTransferSuccess() {
        service.transfer("ACC1", "ACC2", new BigDecimal("30.00"));
        assertEquals(new BigDecimal("70.00"), service.findByNumber("ACC1").getBalance());
        assertEquals(new BigDecimal("80.00"), service.findByNumber("ACC2").getBalance());
    }
}