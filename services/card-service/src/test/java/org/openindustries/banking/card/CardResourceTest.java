package org.openindustries.banking.card;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
class CardResourceTest {

    @Test
    void issuedCardIsMasked() {
        given().contentType("application/json")
                .body("{\"accountId\":\"11111111-1111-1111-1111-111111111111\",\"brand\":\"VISA\"}")
                .when().post("/api/v1/cards")
                .then().statusCode(201)
                .body("maskedPan", startsWith("**** **** ****"))
                .body("status", is("ACTIVE"));
    }

    @Test
    void blockCardWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"accountId\":\"11111111-1111-1111-1111-111111111111\",\"brand\":\"GENERIC\"}")
                .when().post("/api/v1/cards")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/cards/" + id + "/block")
                .then().statusCode(200).body("status", is("BLOCKED"));
    }
}
