package org.openindustries.banking.functions.fraud;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void cleanPaymentIsApproved() {
        given().contentType("application/json")
                .body("{\"paymentId\":\"p-1\",\"amountMinor\":5000,\"currency\":\"USD\",\"paymentsLastHour\":1,\"newPayee\":false,\"unusualHour\":false}")
                .when().post("/fraudSignalScorer")
                .then().statusCode(200).body("decision", is("APPROVE")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreBlocked() {
        given().contentType("application/json")
                .body("{\"paymentId\":\"p-2\",\"amountMinor\":2000000,\"currency\":\"USD\",\"paymentsLastHour\":15,\"newPayee\":true,\"unusualHour\":true}")
                .when().post("/fraudSignalScorer")
                .then().statusCode(200).body("decision", is("BLOCK")).body("score", is(100));
    }
}
