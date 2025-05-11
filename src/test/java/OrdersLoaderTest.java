import Loaders.OrdersLoader;
import Types.OrderInfo;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrdersLoaderTest {

    @Test
    public void shouldLoadOrdersWithMissingPromotions() throws Exception {
        String jsonData = "[\n" +
                "{\"id\": \"ORDER1\", \"value\": 100.00, \"promotions\": [\"mZysk\"]},\n" +
                "{\"id\": \"ORDER4\", \"value\": 50.00}\n" +
                "]";

        Path tempFile = Files.createTempFile("orders", ".json");
        Files.writeString(tempFile, jsonData);

        List<OrderInfo> orderInfos = OrdersLoader.loadFromJson(tempFile.toFile());

        assertThat(orderInfos).hasSize(2);

        assertThat(orderInfos.get(0).id()).isEqualTo("ORDER1");
        assertThat(orderInfos.get(0).value()).isEqualTo(100.00);
        assertThat(orderInfos.get(0).promotions()).containsExactly("mZysk");

        assertThat(orderInfos.get(1).id()).isEqualTo("ORDER4");
        assertThat(orderInfos.get(1).value()).isEqualTo(50.00);
        assertThat(orderInfos.get(1).promotions()).isNullOrEmpty();

        Files.delete(tempFile);
    }
}