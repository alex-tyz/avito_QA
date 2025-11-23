import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.Item;
import org.example.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetItemByIdRestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://qa-internship.avito.com";
    }

    @Test
    public void testGetItemById() {

        Statistics statistics = new Statistics();
        statistics.setLikes(10L);
        statistics.setViewCount(100L);
        statistics.setContacts(5L);

        Item item = new Item();
        item.setSellerId(555123);
        item.setName("Тестовое объявление");
        item.setPrice(5000L);
        item.setStatistics(statistics);

        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(item)
                .when()
                .post("/api/1/item")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String status = createResponse.jsonPath().getString("status");
        String itemId = status.split(" - ")[1];

        given()
                .header("Accept", "application/json")
                .when()
                .get("/api/1/item/" + itemId)
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(itemId))
                .body("[0].name", notNullValue())
                .body("[0].price", notNullValue());
    }
}