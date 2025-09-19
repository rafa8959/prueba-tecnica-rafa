package com.inditex.price.steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PriceStepDefs {

    private Response response;

    @Given("the application is running")
    public void the_application_is_running() {
        // Nothing to do, SpringBootTest starts the app
    }

    @When("I request the price for product {long} and brand {long} at {string}")
    public void i_request_the_price(Long productId, Long brandId, String date) {
        response = given()
                .param("applicationDate", date)
                .param("productId", productId)
                .param("brandId", brandId)
                .when()
                .get("http://localhost:8080/prices");
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer status) {
        assertThat(response.statusCode(), is(status));
    }

    @Then("the price should be {double}")
    public void the_price_should_be(Double expectedPrice) {
        Double actualPrice = response.jsonPath().getDouble("price");
        assertThat(actualPrice, is(expectedPrice));
    }
}
