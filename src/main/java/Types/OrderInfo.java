package Types;

import java.util.List;

public record OrderInfo(String id, double value, List<String> promotions) {

    public Order createProcess() {
        return new Order(this);
    }
}

