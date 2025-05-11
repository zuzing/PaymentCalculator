import Util.TransactionHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionHistoryTest {

    private TransactionHistory history;

    @BeforeEach
    void setUp() {
        history = new TransactionHistory();
    }

    @Test
    void testAddAndGetEntries() {
        history.addEntry("PUNKTY", "ORDER1", 100.0);
        history.addEntry("mZysk", "ORDER2", 200.0);

        List<TransactionHistory.Entry> entries = history.getEntries();

        assertEquals(2, entries.size());
        assertEquals("PUNKTY", entries.get(0).methodId());
        assertEquals("ORDER1", entries.get(0).orderId());
        assertEquals(100.0, entries.get(0).amount());
    }

    @Test
    void testGetTotalAmount() {
        history.addEntry("PUNKTY", "ORDER1", 50.0);
        history.addEntry("mZysk", "ORDER2", 150.0);
        history.addEntry("BosBankrut", "ORDER1", 300.0);

        double total = history.getTotalAmount();
        assertEquals(500.0, total, 0.001);
    }

    @Test
    void testAggregateByMethod() {
        history.addEntry("PUNKTY", "ORDER1", 100.0);
        history.addEntry("PUNKTY", "ORDER2", 50.0);
        history.addEntry("mZysk", "ORDER2", 75.0);
        history.addEntry("BosBankrut", "ORDER1", 25.0);

        Map<String, Double> aggregated = history.aggregateByMethod();

        assertEquals(3, aggregated.size());
        assertEquals(150.0, aggregated.get("PUNKTY"), 0.001);
        assertEquals(75.0, aggregated.get("mZysk"), 0.001);
        assertEquals(25.0, aggregated.get("BosBankrut"), 0.001);
    }

    @Test
    void testUnmodifiableEntriesList() {
        history.addEntry("PUNKTY", "ORDER1", 100.0);
        List<TransactionHistory.Entry> entries = history.getEntries();

        assertThrows(UnsupportedOperationException.class, () -> entries.add(
                new TransactionHistory.Entry("mZysk", "ORDER2", 200.0)
        ));
    }
}
