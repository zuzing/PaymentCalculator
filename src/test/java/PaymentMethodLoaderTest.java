import Loaders.PaymentMethodLoader;
import Types.PaymentMethod;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentMethodLoaderTest {

    @Test
    public void testLoadFromJson_validJson_returnsCorrectList() throws IOException {

        File tempFile = File.createTempFile("payment_methods", ".json");
        tempFile.deleteOnExit();

        String jsonContent = """
            [
              {"id": "PUNKTY", "discount": "15", "limit": "100.00"},
              {"id": "mZysk", "discount": "10", "limit": "180.00"},
              {"id": "BosBankrut", "discount": "5", "limit": "200.00"}
            ]
            """;

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonContent);
        }

        List<PaymentMethod> methods = PaymentMethodLoader.loadFromJson(tempFile);

        assertEquals(3, methods.size());

        PaymentMethod first = methods.get(0);
        assertEquals("PUNKTY", first.getId());
        assertEquals(15, first.getDiscount());
        assertEquals(100.0, first.getLimit());
    }
}