package com.automation.tests.integration;

import com.automation.actions.AssertActions;
import com.automation.endpoints.APIConstants;
import com.automation.modules.PayloadManager;
import com.automation.tests.base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class ITC_1_Create_GetNote extends BaseTest {
    @Test
    public void createNote(ITestContext iTestContext) throws JsonProcessingException {
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.createNote)
                .header("x-auth-token",this.getToken());

        response = requestSpecification.contentType(ContentType.JSON)
                .body(payloadManager.createNote())
                .when()
                .post();
        validatableResponse = response.then().statusCode(200);
        jsonPath = new JsonPath(response.asString());
        String user_id = jsonPath.getString("data.user_id");
        String id = jsonPath.getString("data.id");
        System.out.println(user_id);
        System.out.println(id);
        iTestContext.setAttribute("title",jsonPath.getString("data.title"));
        iTestContext.setAttribute("description",jsonPath.getString("data.description"));
        iTestContext.setAttribute("id",id);
        iTestContext.setAttribute("user_id",user_id);
    }

    @Test(dependsOnMethods = "createNote")
    public void getNote(ITestContext iTestContext) throws JsonProcessingException{
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();
        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.getNotes+"/"+(String) iTestContext.getAttribute("id"))
                .header("x-auth-token",this.getToken());
        response = requestSpecification.contentType(ContentType.JSON)
                .when()
                .get();
        assertActions.verifyStatusCode(response);
        validatableResponse = response.then().statusCode(200).log().all();
        jsonPath = new JsonPath(response.asString());
        assertActions.verifyResponseBody((String) iTestContext.getAttribute("title"),jsonPath.getString("data.title"),"Title Not Matched!");
    }
}
