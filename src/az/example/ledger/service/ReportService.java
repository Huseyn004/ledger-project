package az.example.ledger.service;

import az.example.ledger.model.Account;
import az.example.ledger.model.Customer;
import az.example.ledger.model.Transaction;
import az.example.ledger.repository.AccountRepository;
import az.example.ledger.util.SortUtils;

import java.math.BigDecimal;
import java.util.*;

public class ReportService {
    private final AccountRepository repository;
    private final Map<String, Customer> customerMap = new HashMap<>();

    public ReportService(AccountRepository repository) {
        this.repository = repository;
    }

    public void registerCustomer(Customer customer) {
        customerMap.put(customer.getId(), customer);
    }

    public Optional<Customer> getCustomer(String customerId) {
        return Optional.ofNullable(customerMap.get(customerId));
    }

    public List<Account> getTopAccountsByBalance(int limit) {
        List<Account> accounts = new ArrayList<>(repository.findAll());
        accounts.sort((a1, a2) -> a2.getBalance().compareTo(a1.getBalance()));
        return accounts.subList(0, Math.min(limit, accounts.size()));
    }

    public BigDecimal getTotalBalanceForCustomer(String customerId) {
        Customer customer = customerMap.get(customerId);
        if (customer == null) return BigDecimal.ZERO;
        return customer.getAccounts().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> sortTransactionsByAmount(List<Transaction> transactions) {
        List<Transaction> sorted = new ArrayList<>(transactions);
        SortUtils.mergeSortByAmount(sorted);
        return sorted;
    }
}