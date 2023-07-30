package com.automation.tests.crud;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class TC01_HealthCheckup {

    @Test
    public void healthCheck(){
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri("https://practice.expandtesting.com/notes/api")
                .basePath("/health-check")
                .accept("application/json");

        Response response = requestSpecification.when().get();



    }
}
