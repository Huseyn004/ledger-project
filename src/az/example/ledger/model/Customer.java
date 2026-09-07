package az.example.ledger.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String id;
    private String name;
    private String surname;
    private final List<Account> accounts;

    public Customer(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.accounts = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public List<Account> getAccounts() { return accounts; }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public String toString() {
        return "Customer{id='" + id + "', name='" + name + "', surname='" + surname + "'}";
    }
}