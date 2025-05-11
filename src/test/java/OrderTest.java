import Types.Order;
import Types.OrderInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private OrderInfo orderInfo;
    private Order order;

    @BeforeEach
    public void setUp() {
        orderInfo = new OrderInfo("ORDER1", 100.0, List.of("mZysk"));
        order = new Order(orderInfo);
    }

    @Test
    public void testGetOrderId() {
        assertEquals("ORDER1", order.getId());
    }

    @Test
    public void testGetOriginalValue() {
        assertEquals(100.0, order.getOriginalValue());
    }

    @Test
    public void testGetPromotions() {
        assertEquals(List.of("mZysk"), order.getPromotions());
    }

    @Test
    public void testInitialCurrentValueEqualsOriginalValue() {
        assertEquals(100.0, order.getCurrentValue());
    }

    @Test
    public void testInitialAmountPaidIsZero() {
        assertEquals(0.0, order.getAmountPaid());
    }

    @Test
    public void testPayIncreasesAmountPaid() {
        order.pay(25.0);
        assertEquals(25.0, order.getAmountPaid());
    }

    @Test
    public void testGetLeftToPay() {
        order.pay(30.0);
        assertEquals(70.0, order.getLeftToPay(), 0.001);
    }

    @Test
    public void testIsPaidFalseWhenNotFullyPaid() {
        order.pay(50.0);
        assertFalse(order.isPaid());
    }

    @Test
    public void testIsPaidTrueWhenFullyPaid() {
        order.pay(100.0);
        assertTrue(order.isPaid());
    }

    @Test
    public void testNoDiscountAppliedInitially() {
        assertTrue(order.noDiscountApplied());
    }

    @Test
    public void testApplyDiscountChangesCurrentValue() {
        order.applyDiscount(0.1);
        assertEquals(90.0, order.getCurrentValue(), 0.001);
    }

    @Test
    public void testNoDiscountAppliedAfterDiscount() {
        order.applyDiscount(0.2);
        assertFalse(order.noDiscountApplied());
    }

    @Test
    public void testDiscountValueWithDiscount() {
        assertEquals(10.0, order.discountValue(0.1), 0.001);
    }

    @Test
    public void testDiscountValueReturnsZeroWhenAlreadyDiscounted() {
        order.applyDiscount(0.1);
        assertEquals(0.0, order.discountValue(0.2));
    }

    @Test
    public void testValueAfterDiscount() {
        assertEquals(90.0, order.valueAfterDiscount(0.1), 0.001);
    }

    @Test
    public void testIfOnlyTheFirstDiscountApplies() {
        order.applyDiscount(0.2);
        order.applyDiscount(0.1);
        assertEquals(80.0, order.getCurrentValue(), 0.001);

    }
}
