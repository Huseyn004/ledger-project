package az.example.ledger.repository;

import az.example.ledger.model.Account;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> storage = new HashMap<>();

    @Override
    public void save(Account account) {
        storage.put(account.getAccountNumber(), account);
    }

    @Override
    public Optional<Account> findByNumber(String accountNumber) {
        return Optional.ofNullable(storage.get(accountNumber));
    }

    @Override
    public Collection<Account> findAll() {
        return storage.values();
    }
}