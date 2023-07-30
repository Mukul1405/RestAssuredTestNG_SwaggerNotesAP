package com.automation.tests.crud;

import com.automation.actions.AssertActions;
import com.automation.endpoints.APIConstants;
import com.automation.modules.PayloadManager;
import com.automation.tests.base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class TC02_RegisterUser extends BaseTest {

    @Test
    public void registerUser() throws JsonProcessingException {
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();
        System.out.println(payloadManager.createRegisterationPayload());
        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.register);


        response = requestSpecification.contentType(ContentType.JSON)
                .body(payloadManager.createRegisterationPayload()).when().post();
        assertActions.verifyStatusCode(response);
        validatableResponse = response.then().log().all();
    }
}
