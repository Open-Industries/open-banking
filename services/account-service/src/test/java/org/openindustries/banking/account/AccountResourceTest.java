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
}
