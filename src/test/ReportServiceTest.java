package az.example.ledger.test;

import az.example.ledger.model.Account;
import az.example.ledger.model.CurrentAccount;
import az.example.ledger.util.SortUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    @Test
    void testSortByBalance() {
        List<Account> accounts = List.of(
                new CurrentAccount("A1", new BigDecimal("10.00")),
                new CurrentAccount("A2", new BigDecimal("100.00"))
        );
        List<Account> sorted = SortUtils.sortByBalanceDescending(accounts);
        assertEquals("A2", sorted.get(0).getAccountNumber());
    }
}