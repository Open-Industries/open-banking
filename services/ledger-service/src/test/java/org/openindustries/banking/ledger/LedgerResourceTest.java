package org.openindustries.banking.ledger;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LedgerResourceTest {

    private static final String BALANCED = "{"
            + "\"reference\":\"test-balanced-1\",\"description\":\"transfer\",\"postings\":["
            + "{\"accountId\":\"11111111-1111-1111-1111-111111111111\",\"direction\":\"DEBIT\",\"amountMinor\":500,\"currency\":\"USD\"},"
            + "{\"accountId\":\"22222222-2222-2222-2222-222222222222\",\"direction\":\"CREDIT\",\"amountMinor\":500,\"currency\":\"USD\"}]}";

    private static final String UNBALANCED = "{"
            + "\"reference\":\"test-unbalanced-1\",\"description\":\"bad\",\"postings\":["
            + "{\"accountId\":\"11111111-1111-1111-1111-111111111111\",\"direction\":\"DEBIT\",\"amountMinor\":500,\"currency\":\"USD\"},"
            + "{\"accountId\":\"22222222-2222-2222-2222-222222222222\",\"direction\":\"CREDIT\",\"amountMinor\":400,\"currency\":\"USD\"}]}";

    @Test
    void balancedEntryIsBooked() {
        given().contentType("application/json").body(BALANCED)
                .when().post("/api/v1/ledger/journal-entries")
                .then().statusCode(201);
    }

    @Test
    void unbalancedEntryIsRejected() {
        given().contentType("application/json").body(UNBALANCED)
                .when().post("/api/v1/ledger/journal-entries")
                .then().statusCode(422).body("status", is(422));
    }
}
