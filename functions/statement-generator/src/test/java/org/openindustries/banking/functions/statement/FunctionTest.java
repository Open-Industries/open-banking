package org.openindustries.banking.functions.statement;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void generatesSummary() {
        given().contentType("application/json")
                .body("{\"accountId\":\"a-1\",\"period\":\"2026-10\",\"currency\":\"USD\",\"postings\":["
                        + "{\"direction\":\"CREDIT\",\"amountMinor\":1000,\"currency\":\"USD\"},"
                        + "{\"direction\":\"DEBIT\",\"amountMinor\":300,\"currency\":\"USD\"}]}")
                .when().post("/statementGenerator")
                .then().statusCode(200)
                .body("netMinor", is(700))
                .body("postingCount", is(2));
    }
}
