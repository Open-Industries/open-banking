package org.openindustries.banking.functions.kyc;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void completeSubmissionIsVerified() {
        given().contentType("application/json")
                .body("{\"customerId\":\"c-1\",\"governmentIdPresent\":true,\"governmentIdExpired\":false,\"proofOfAddressPresent\":true,\"selfiePresent\":true}")
                .when().post("/kycDocumentProcessor")
                .then().statusCode(200).body("verified", is(true));
    }

    @Test
    void expiredIdIsRejected() {
        given().contentType("application/json")
                .body("{\"customerId\":\"c-2\",\"governmentIdPresent\":true,\"governmentIdExpired\":true,\"proofOfAddressPresent\":true,\"selfiePresent\":true}")
                .when().post("/kycDocumentProcessor")
                .then().statusCode(200).body("verified", is(false)).body("findings.size()", is(1));
    }
}
