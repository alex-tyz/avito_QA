import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.Item;
import org.example.Statistics;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class SellerItemsHttpClientTest {

    private final String BASE_URL = "https://qa-internship.avito.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Test
    public void testGetSellerItems() throws Exception {

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


        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/1/555123/item"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        assertTrue(jsonArray.size() > 0);

        JsonObject firstItem = jsonArray.get(0).getAsJsonObject();
        assertEquals(555123, firstItem.get("sellerId").getAsInt());
    }
}