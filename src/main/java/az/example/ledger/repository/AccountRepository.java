package az.example.ledger.repository;

import az.example.ledger.model.Account;
import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findByNumber(String accountNumber);
    Collection<Account> findAll();
}