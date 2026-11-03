package org.openindustries.banking.customer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class CustomerResourceTest {

    @Test
    void onboardAndFetchCustomer() {
        String id = given()
                .contentType("application/json")
                .body("{\"type\":\"INDIVIDUAL\",\"fullName\":\"Ada Byron\",\"documentId\":\"DOC-1\",\"email\":\"ada@example.org\"}")
                .when().post("/api/v1/customers")
                .then().statusCode(201)
                .body("kycStatus", is("PENDING"))
                .body("id", notNullValue())
                .extract().path("id");

        given().when().get("/api/v1/customers/" + id)
                .then().statusCode(200).body("fullName", is("Ada Byron"));
    }

    @Test
    void unknownCustomerIsAProblem() {
        given().when().get("/api/v1/customers/00000000-0000-0000-0000-000000000000")
                .then().statusCode(404).body("status", is(404));
    }
}
