package org.openindustries.banking.account;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AccountResourceTest {

    @Test
    void openAccountWithInitialDeposit() {
        given().contentType("application/json")
                .body("{\"customerId\":\"11111111-1111-1111-1111-111111111111\",\"type\":\"CHECKING\",\"currency\":\"USD\",\"initialDepositMinor\":10000}")
                .when().post("/api/v1/accounts")
                .then().statusCode(201).body("balanceMinor", is(10000)).body("status", is("ACTIVE"));
    }

    @Test
    void blockAccountWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"customerId\":\"11111111-1111-1111-1111-111111111111\",\"type\":\"SAVINGS\",\"currency\":\"USD\",\"initialDepositMinor\":0}")
                .when().post("/api/v1/accounts")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/accounts/" + id + "/block")
                .then().statusCode(200).body("status", is("BLOCKED"));
    }
}
