package Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionHistory {

    public record Entry(String methodId, String orderId, double amount) {
    }

    private final List<Entry> entries;

    public TransactionHistory() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(String methodId, String orderId, double amount) {
        Entry entry = new Entry(methodId, orderId, amount);
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public double getTotalAmount() {
        return entries.stream().mapToDouble(Entry::amount).sum();
    }

    public Map<String, Double> aggregateByMethod() {
        Map<String, Double> aggregation = new HashMap<>();
        for (Entry entry : entries) {
            aggregation.merge(entry.methodId(), entry.amount(), Double::sum);
        }
        return aggregation;
    }

    public void printAggregatedData() {
        Map<String, Double> aggregated = aggregateByMethod();
        for (Map.Entry<String, Double> entry : aggregated.entrySet()) {
            System.out.printf("%s %.2f%n", entry.getKey(), entry.getValue());
        }
    }
}