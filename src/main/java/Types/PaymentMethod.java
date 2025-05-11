package Types;

public abstract class PaymentMethod {
    private String id;

    private double discount;
    private double limit;

    private double charged = 0;

    public PaymentMethod(){}
    public PaymentMethod(String id, int discount, double limit) {
        this.id = id;
        this.discount = (double) discount / 100;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public double getDiscount() {
        return discount;
    }

    public double getLimit() {
        return limit;
    }

    public double getCharge(){ return charged; }

    public double getLeft(){ return limit-charged;}
    public void charge(double amount){
        if (amount > getLeft()) {
            throw new IllegalArgumentException("Amount exceeds limit");
        }
        this.charged += amount;
    }

    public double calculateDiscount(Order order){
        return order.discountValue(discount);
    }
}
