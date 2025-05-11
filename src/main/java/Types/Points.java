package Types;

public class Points extends PaymentMethod {

    private static final double DISCOUNT_PERCENTAGE_CONDITION = 0.1;
    public static final double PARTIAL_DISCOUNT_PERCENTAGE = 0.1;
    public Points(String id, int discount, double limit) {
        super(id, discount, limit);
    }

    public double calculatePartialDiscount(Order order) {
        if (getLeft() >= order.getOriginalValue() * DISCOUNT_PERCENTAGE_CONDITION) {
            return order.discountValue(PARTIAL_DISCOUNT_PERCENTAGE);
        } else {
            return 0;
        }
    }
}
