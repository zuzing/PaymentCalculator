package Loaders;

import Types.Card;
import Types.PaymentMethod;
import Types.Points;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodLoader {
    public static List<PaymentMethod> loadFromJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        List<PaymentMethod> result = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(file);
            for (JsonNode node : root) {
                String id = node.get("id").asText();
                int discount = node.get("discount").asInt();
                double limit = node.get("limit").asDouble();

                if ("PUNKTY".equalsIgnoreCase(id)) {
                    result.add(new Points(id, discount, limit));
                } else {
                    result.add(new Card(id, discount, limit));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}