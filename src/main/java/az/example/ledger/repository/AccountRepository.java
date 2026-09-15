package az.example.ledger.repository;

import az.example.ledger.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByNumber(String number);
    List<Account> findAll();
    void save(Account account);
}