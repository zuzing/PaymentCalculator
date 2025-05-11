package Loaders;

import Types.PaymentMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PaymentMethodLoader {
    public static List<PaymentMethod> loadFromJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, new TypeReference<List<PaymentMethod>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
