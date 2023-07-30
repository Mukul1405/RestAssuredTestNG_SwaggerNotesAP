package com.automation.tests.crud;

import com.automation.actions.AssertActions;
import com.automation.endpoints.APIConstants;
import com.automation.modules.PayloadManager;
import com.automation.tests.base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class TC03_LoginUser extends BaseTest {

    @Test
    public void loginUser() throws JsonProcessingException {
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
    }
}
