package org.openindustries.banking.loan;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LoanResourceTest {

    @Test
    void applyAndSchedule() {
        String id = given().contentType("application/json")
                .body("{\"customerId\":\"11111111-1111-1111-1111-111111111111\",\"principalMinor\":120000,\"currency\":\"USD\",\"termMonths\":12,\"annualRateBps\":0}")
                .when().post("/api/v1/loans")
                .then().statusCode(201).body("status", is("APPLIED"))
                .extract().path("id");

        given().when().get("/api/v1/loans/" + id + "/schedule")
                .then().statusCode(200)
                .body("size()", is(12))
                .body("[0].paymentMinor", is(10000))
                .body("[11].balanceAfterMinor", is(0));
    }
}
