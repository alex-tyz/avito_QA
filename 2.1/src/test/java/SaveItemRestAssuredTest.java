import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.Item;
import org.example.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SaveItemRestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://qa-internship.avito.com";
    }

    @Test
    public void testSaveItem() {
        Statistics statistics = new Statistics();
        statistics.setLikes(10L);
        statistics.setViewCount(100L);
        statistics.setContacts(5L);

        Item item = new Item();
        item.setSellerId(555123);
        item.setName("Тестовое объявление");
        item.setPrice(5000L);
        item.setStatistics(statistics);

        given()
                .contentType(ContentType.JSON)
                .body(item)
                .when()
                .post("/api/1/item")
                .then()
                .statusCode(200)
                .body("status", containsString("Сохранили объявление"));
    }
}