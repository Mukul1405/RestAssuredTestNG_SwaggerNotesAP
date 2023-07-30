package com.automation.tests.base;

import com.automation.actions.AssertActions;
import com.automation.endpoints.APIConstants;
import com.automation.modules.PayloadManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class BaseTest {

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public Response response;
    public JsonPath jsonPath;
    public ValidatableResponse validatableResponse;

    @BeforeSuite
    public void healthCheck(){
        requestSpecification = RestAssured.given()
                .baseUri(APIConstants.baseURI)
                .basePath(APIConstants.heathCheck);
        response = requestSpecification.get();
        validatableResponse = response.then().statusCode(200);
    }

    public String getToken() throws JsonProcessingException {
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();
//        System.out.println(payloadManager.createLoginPayload());
        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.login);

        response = requestSpecification.contentType(ContentType.JSON)
                .body(payloadManager.createLoginPayload())
                .when()
                .post();

        assertActions.verifyStatusCode(response);
        validatableResponse = response.then().statusCode(200);
//        assertActions.verifyResponseBody(jsonPath.get("data.email"), payloadManager.getRegisteredEmailPayloadValue(), "Valid Email");
        jsonPath = new JsonPath(response.asString());
        String token = jsonPath.getString("data.token");
        System.out.println(token);
        return token;
    }
}

