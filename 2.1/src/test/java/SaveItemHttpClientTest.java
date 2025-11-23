import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.Item;
import org.example.Statistics;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class SaveItemHttpClientTest {

    private final String BASE_URL = "https://qa-internship.avito.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Test
    public void testSaveItem() throws Exception {
        Statistics statistics = new Statistics();
        statistics.setLikes(10L);
        statistics.setViewCount(100L);
        statistics.setContacts(5L);

        Item item = new Item();
        item.setSellerId(555123);
        item.setName("Тестовое объявление");
        item.setPrice(5000L);
        item.setStatistics(statistics);

        String requestBody = gson.toJson(item);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/1/item"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
        String status = jsonResponse.get("status").getAsString();
        assertTrue(status.contains("Сохранили объявление"));
    }
}