import Loaders.*;
import Types.OrderInfo;
import Types.PaymentMethod;
import Algorithms.GreedyAlgorithm;

import java.io.File;
import java.util.List;

public class PaymentCalculator {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar app.jar <orders.json path> <paymentmethods.json path>");
            System.exit(1);
        }

        File ordersFile = new File(args[0]);
        File paymentMethodsFile = new File(args[1]);

        List<OrderInfo> orderInfos = OrdersLoader.loadFromJson(ordersFile);
        List<PaymentMethod> paymentMethods = PaymentMethodLoader.loadFromJson(paymentMethodsFile);

        GreedyAlgorithm algorithm = new GreedyAlgorithm(paymentMethods, orderInfos);
        algorithm.run();
        algorithm.history.printAggregatedData();
    }
}