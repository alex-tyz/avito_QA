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

public class ItemStatisticsHttpClientTest {

    private final String BASE_URL = "https://qa-internship.avito.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Test
    public void testGetItemStatistics() throws Exception {

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

        HttpRequest createRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/1/item"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> createResponse = client.send(createRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, createResponse.statusCode());

        JsonObject createJson = gson.fromJson(createResponse.body(), JsonObject.class);

        String status = createJson.get("status").getAsString();
        String itemId = status.split(" - ")[1];


        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/1/statistic/" + itemId))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("contacts"));
        assertTrue(response.body().contains("likes"));
        assertTrue(response.body().contains("viewCount"));
    }
}