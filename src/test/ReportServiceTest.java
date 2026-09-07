package test;

import az.example.ledger.model.Transaction;
import az.example.ledger.model.TransactionType;
import az.example.ledger.util.SearchUtils;
import az.example.ledger.util.SortUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    @Test
    void benchmarkSearchAndSortAlgorithms() {
        int count = 50000;
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String id = String.format("TX-%06d", i);
            BigDecimal amount = new BigDecimal((int) (Math.random() * 10000));
            transactions.add(new Transaction(id, "ACC-001", TransactionType.DEPOSIT, amount));
        }

        // Measure Linear Search
        long start = System.nanoTime();
        SearchUtils.linearSearch(transactions, "TX-049999");
        long linearTime = System.nanoTime() - start;

        // Measure Binary Search (list sorted by ID)
        transactions.sort(Comparator.comparing(Transaction::getId));
        start = System.nanoTime();
        SearchUtils.binarySearch(transactions, "TX-049999");
        long binaryTime = System.nanoTime() - start;

        // Measure Merge Sort vs Insertion Sort on subset
        List<Transaction> mergeList = new ArrayList<>(transactions.subList(0, 5000));
        List<Transaction> insertList = new ArrayList<>(mergeList);

        start = System.nanoTime();
        SortUtils.insertionSortByAmount(insertList);
        long insertionSortTime = System.nanoTime() - start;

        start = System.nanoTime();
        SortUtils.mergeSortByAmount(mergeList);
        long mergeSortTime = System.nanoTime() - start;

        System.out.println("=== ALGORITHM BENCHMARK RESULTS ===");
        System.out.printf("Linear Search (50k items): %d ns%n", linearTime);
        System.out.printf("Binary Search (50k items): %d ns%n", binaryTime);
        System.out.printf("Insertion Sort (5k items): %d ns%n", insertionSortTime);
        System.out.printf("Merge Sort (5k items):     %d ns%n", mergeSortTime);

        assertTrue(binaryTime <= linearTime);
        assertTrue(mergeSortTime <= insertionSortTime);
    }
}