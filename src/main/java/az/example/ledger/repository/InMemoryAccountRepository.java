package az.example.ledger.repository;

import az.example.ledger.model.Account;

import java.util.*;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<String, Account> storage = new HashMap<>();

    @Override
    public Optional<Account> findByNumber(String number) {
        return Optional.ofNullable(storage.get(number));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void save(Account account) {
        storage.put(account.getAccountNumber(), account);
    }
}