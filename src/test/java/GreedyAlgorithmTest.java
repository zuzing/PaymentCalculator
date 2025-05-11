import Algorithms.GreedyAlgorithm;
import Types.*;
import Util.TransactionHistory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreedyAlgorithmTest {

    @Test
    public void testGreedyAlgorithmWithSampleData() {
        List<OrderInfo> orders = Arrays.asList(
                new OrderInfo("ORDER1", 100.00, Arrays.asList("mZysk")),
                new OrderInfo("ORDER2", 200.00, Arrays.asList("BosBankrut")),
                new OrderInfo("ORDER3", 150.00, Arrays.asList("mZysk", "BosBankrut")),
                new OrderInfo("ORDER4", 50.00, List.of())
        );

        List<PaymentMethod> paymentMethods = Arrays.asList(
                new Points("PUNKTY", 15, 100.00),
                new Card("mZysk", 10, 180.00),
                new Card("BosBankrut", 5, 200.00)
        );

        GreedyAlgorithm algorithm = new GreedyAlgorithm(paymentMethods, orders);
        algorithm.run();

        TransactionHistory history = algorithm.history;
        double totalAmount = history.getTotalAmount();

        assertTrue(round(totalAmount) <= 480.00, "Total amount should be less than or equal to 480.00");
    }

    @Test
    public void testGreedyAlgorithmWithInsufficientFunds() {
        List<OrderInfo> orders = Arrays.asList(
                new OrderInfo("ORDER1", 100.00, Arrays.asList("CardA")),
                new OrderInfo("ORDER2", 150.00, Arrays.asList("CardB")),
                new OrderInfo("ORDER3", 200.00, Arrays.asList("CardA", "CardB"))
        );

        List<PaymentMethod> paymentMethods = Arrays.asList(
                new Points("PUNKTY", 10, 50.00),
                new Card("CardA", 10, 80.00),
                new Card("CardB", 5, 70.00)
        );

        GreedyAlgorithm algorithm = new GreedyAlgorithm(paymentMethods, orders);
        algorithm.run();

        double totalFunds = paymentMethods.stream().mapToDouble(PaymentMethod::getLeft).sum();
        double totalPaid = algorithm.history.getTotalAmount();

        assertTrue(totalPaid < 450.00, "Total paid should be less than total order cost (450.00)");
        assertTrue(totalPaid <= totalFunds, "Total paid must not exceed available funds");

        boolean atLeastOneUnpaid = orders.stream()
                .map(OrderInfo::createProcess)
                .anyMatch(order -> !order.isPaid());
        assertTrue(atLeastOneUnpaid, "At least one order should remain unpaid due to insufficient funds");
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
