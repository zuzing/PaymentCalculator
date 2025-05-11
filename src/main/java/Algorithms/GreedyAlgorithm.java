package Algorithms;

import Types.*;
import Util.TransactionHistory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GreedyAlgorithm {

    private final List<Order> orders;
    private final List<PaymentMethod> paymentMethods;
    private final Points points;
    public final TransactionHistory history;

    public GreedyAlgorithm(List<PaymentMethod> paymentMethods, List<OrderInfo> orderInfos) {
        this.orders = orderInfos.stream().map(OrderInfo::createProcess).collect(Collectors.toList());
        this.points = paymentMethods.stream()
                .filter(method -> "PUNKTY".equals(method.getId()))
                .findFirst()
                .map(method -> (Points) method)
                .orElse(new Points("POINTS", 0, 0));
        this.paymentMethods = paymentMethods.stream()
                .filter(method -> !"PUNKTY".equals(method.getId()))
                .collect(Collectors.toList());
        this.history = new TransactionHistory();
    }

    public void run(){
        while (!orders.isEmpty()) {
            Order bestOrder = null;
            PaymentMethod bestMethod = null;
            double maxDiscount = 0;
            double payAmount = 0;

            for (Order order : orders) {
                for (PaymentMethod method : paymentMethods) {
                    double discount = method.calculateDiscount(order);

                    if (discount > maxDiscount && canPay(method, order)) {
                        maxDiscount = discount;
                        bestOrder = order;
                        bestMethod = method;
                        payAmount = order.valueAfterDiscount(method.getDiscount());
                    }
                }

                // Check paying with points completely
                if (canPay(points, order)) {
                    double discountPoints = points.calculateDiscount(order);
                    if (discountPoints > maxDiscount) {
                        maxDiscount = discountPoints;
                        bestOrder = order;
                        bestMethod = points;
                        payAmount = order.valueAfterDiscount(points.getDiscount());
                    }
                }

                double partialDiscount = points.calculatePartialDiscount(order);
                if (partialDiscount > maxDiscount) {
                    maxDiscount = partialDiscount;
                    bestOrder = order;
                    bestMethod = points;
                    payAmount = partialDiscount;
                }
            }

            if (bestOrder == null || bestMethod == null) break;

            pay(bestMethod, bestOrder, payAmount);
            if (bestOrder.isPaid()) {
                orders.remove(bestOrder);
            }
        }

        for(Order order: orders){
            for (PaymentMethod method : paymentMethods.stream()
                    .sorted(Comparator.comparingDouble(PaymentMethod::getLeft))
                    .toList()){
                if(canPay(method, order, points)){
                    pay(method, order, method.getLeft());
                    pay(points, order, order.getLeftToPay());
                }
            }
            if(!order.isPaid()){
                System.out.println("Did not manage to pay for all orders");
            }
        }

    }

    private boolean canPay(PaymentMethod method, Order order) {
        return method.getLeft() >= order.valueAfterDiscount(method.getDiscount());
    }

    private boolean canPay(PaymentMethod method, Order order, Points points){
        return method.getLeft() + points.getLeft() >= order.getCurrentValue();
    }

    private void pay(PaymentMethod method,Order order, double amount) {
        if (method.getLeft() > amount && method instanceof Points) {
            order.applyDiscount(Points.PARTIAL_DISCOUNT_PERCENTAGE);
        } else {
            order.applyDiscount(method.getDiscount());
        }
        order.pay(amount);
        method.charge(amount);
        history.addEntry(method.getId(), order.getId(), amount);
    }
}

