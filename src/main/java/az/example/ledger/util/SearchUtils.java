package az.example.ledger.util;

import az.example.ledger.model.Transaction;
import java.util.List;
import java.util.Optional;

public class SearchUtils {

    // Linear Search - O(n)
    public static Optional<Transaction> linearSearch(List<Transaction> transactions, String id) {
        for (Transaction tx : transactions) {
            if (tx.getId().equals(id)) {
                return Optional.of(tx);
            }
        }
        return Optional.empty();
    }

    // Binary Search - O(log n) (requires list to be sorted by Transaction ID)
    public static Optional<Transaction> binarySearch(List<Transaction> transactions, String id) {
        int low = 0;
        int high = transactions.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Transaction midTx = transactions.get(mid);
            int cmp = midTx.getId().compareTo(id);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return Optional.of(midTx);
            }
        }
        return Optional.empty();
    }
}