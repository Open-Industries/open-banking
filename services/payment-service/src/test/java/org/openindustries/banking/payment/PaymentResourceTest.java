package org.openindustries.banking.payment;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class PaymentResourceTest {

    private static final String BODY = "{\"debitAccountId\":\"11111111-1111-1111-1111-111111111111\","
            + "\"creditAccountId\":\"22222222-2222-2222-2222-222222222222\",\"amountMinor\":2500,\"currency\":\"USD\",\"rail\":\"INTERNAL\"}";

    @Test
    void fullLifecycleAndIdempotency() {
        String id = given().contentType("application/json").header("Idempotency-Key", "pay-test-1").body(BODY)
                .when().post("/api/v1/payments")
                .then().statusCode(201).body("status", is("CREATED"))
                .extract().path("id");

        // Retrying with the same key returns the same payment instead of duplicating it.
        given().contentType("application/json").header("Idempotency-Key", "pay-test-1").body(BODY)
                .when().post("/api/v1/payments")
                .then().statusCode(201).body("id", is(id));

        given().when().post("/api/v1/payments/" + id + "/authorize")
                .then().statusCode(200).body("status", is("AUTHORIZED"));
        given().when().post("/api/v1/payments/" + id + "/execute")
                .then().statusCode(200).body("status", is("EXECUTED"));
    }

    @Test
    void missingIdempotencyKeyIsRejected() {
        given().contentType("application/json").body(BODY)
                .when().post("/api/v1/payments")
                .then().statusCode(400);
    }
}
