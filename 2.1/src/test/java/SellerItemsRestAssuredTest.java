import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.Item;
import org.example.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SellerItemsRestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://qa-internship.avito.com";
    }

    @Test
    public void testGetSellerItems() {

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
                .statusCode(200);


        given()
                .header("Accept", "application/json")
                .when()
                .get("/api/1/555123/item")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].sellerId", equalTo(555123));
    }

    @Test
    public void testSellerItemsHaveRequiredFields() {
        given()
                .header("Accept", "application/json")
                .when()
                .get("/api/1/555123/item")
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].price", notNullValue());
    }
}