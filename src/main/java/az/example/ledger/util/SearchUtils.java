package az.example.ledger.util;

import az.example.ledger.model.Account;

import java.util.List;

public class SearchUtils {
    public static List<Account> filterByType(List<Account> accounts, String type) {
        return accounts.stream()
                .filter(a -> a.getType().equalsIgnoreCase(type))
                .toList();
    }
}