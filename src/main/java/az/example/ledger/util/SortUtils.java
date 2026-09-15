package az.example.ledger.util;

import az.example.ledger.model.Account;

import java.util.Comparator;
import java.util.List;

public class SortUtils {
    public static List<Account> sortByBalanceDescending(List<Account> accounts) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getBalance).reversed())
                .toList();
    }
}