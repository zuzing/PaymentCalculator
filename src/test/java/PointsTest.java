import Types.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PointsTest {

    @Test
    public void testCalculatePartialDiscountEnoughPoints() {
        Order order = new Order(new OrderInfo("ORDER1", 100.0, List.of()));
        Points points = new Points("PUNKTY", 15, 100.0);

        double discount = points.calculatePartialDiscount(order);

        assertEquals(10.0, discount, 0.001);
    }

    @Test
    public void testCalculatePartialDiscountNotEnoughPoints() {
        Order order = new Order(new OrderInfo("ORDER1", 100.0, List.of()));
        Points points = new Points("PUNKTY", 15, 5.0);

        double discount = points.calculatePartialDiscount(order);

        assertEquals(0.0, discount, 0.001);
    }

    @Test
    public void testGetLeftAndCharge() {
        Points points = new Points("PUNKTY", 15, 100.0);

        assertEquals(100.0, points.getLeft(), 0.001);

        points.charge(40.0);
        assertEquals(60.0, points.getLeft(), 0.001);

        points.charge(60.0);
        assertEquals(0.0, points.getLeft(), 0.001);
    }

    @Test
    public void testChargeBeyondLimitThrowsException() {
        Points points = new Points("PUNKTY", 15, 50.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            points.charge(60.0);
        });

        assertTrue(exception.getMessage().contains("Amount exceeds limit"));
    }

    @Test
    public void testCalculateDiscountStandardBehavior() {
        Order order = new Order(new OrderInfo( "ORDER1", 200.0, List.of()));
        Points points = new Points("PUNKTY", 15, 100.0);

        double discount = points.calculateDiscount(order);

        assertEquals(30.0, discount, 0.001);
    }
}
