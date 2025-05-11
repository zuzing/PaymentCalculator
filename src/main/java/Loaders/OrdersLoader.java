package Loaders;

import Types.OrderInfo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrdersLoader {

    public static List<OrderInfo> loadFromJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, new TypeReference<List<OrderInfo>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load orders from JSON file", e);
        }
    }
}

