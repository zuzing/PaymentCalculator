package Types;

import java.util.List;

public class Order {
    private final OrderInfo orderInfo;
    private double currentValue;
    private double amountPaid;

    private final double EPS = 0.001;

    public Order(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        this.currentValue = orderInfo.value();
        this.amountPaid = 0;
    }

    public String getId() {
        return orderInfo.id();
    }

    public double getOriginalValue() {
        return orderInfo.value();
    }

    public List<String> getPromotions() {
        return orderInfo.promotions();
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void pay(double amount){
        this.amountPaid+=amount;
    }

    public double getLeftToPay(){
        return currentValue - amountPaid;
    }

    public boolean isPaid(){
        return Math.abs(amountPaid - currentValue) < EPS;
    }

    public boolean noDiscountApplied() {
        return (Math.abs(orderInfo.value() - currentValue) < EPS);
    }

    public double discountValue(double percentage){
        if(noDiscountApplied()){
            return getOriginalValue() * percentage;
        }
        else{
            return 0;
        }
    }

    public double valueAfterDiscount(double percentage){
        if(noDiscountApplied()) {
            return getOriginalValue() - discountValue(percentage);
        }
        else{
            return currentValue;
        }
    }

    public void applyDiscount(double percentage){
        this.currentValue = valueAfterDiscount(percentage);
    }
}