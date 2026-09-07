package az.example.ledger.util;

import az.example.ledger.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class SortUtils {

    // Insertion Sort by Amount - O(n^2)
    public static void insertionSortByAmount(List<Transaction> transactions) {
        int n = transactions.size();
        for (int i = 1; i < n; i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;
            while (j >= 0 && transactions.get(j).getAmount().compareTo(key.getAmount()) > 0) {
                transactions.set(j + 1, transactions.get(j));
                j--;
            }
            transactions.set(j + 1, key);
        }
    }

    // Merge Sort by Amount - O(n log n)
    public static void mergeSortByAmount(List<Transaction> transactions) {
        if (transactions.size() < 2) return;
        int mid = transactions.size() / 2;

        List<Transaction> left = new ArrayList<>(transactions.subList(0, mid));
        List<Transaction> right = new ArrayList<>(transactions.subList(mid, transactions.size()));

        mergeSortByAmount(left);
        mergeSortByAmount(right);

        merge(transactions, left, right);
    }

    private static void merge(List<Transaction> result, List<Transaction> left, List<Transaction> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getAmount().compareTo(right.get(j).getAmount()) <= 0) {
                result.set(k++, left.get(i++));
            } else {
                result.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) result.set(k++, left.get(i++));
        while (j < right.size()) result.set(k++, right.get(j++));
    }
}