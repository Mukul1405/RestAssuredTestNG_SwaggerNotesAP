package com.automation.tests.crud;

import com.automation.actions.AssertActions;
import com.automation.endpoints.APIConstants;
import com.automation.modules.PayloadManager;
import com.automation.tests.base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC04_CRUD extends BaseTest {

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
        iTestContext.setAttribute("id",id);
        iTestContext.setAttribute("user_id",user_id);
    }

    @Test
    public void getNotes() throws JsonProcessingException{
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();
        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.getNotes)
                .header("x-auth-token",this.getToken());
        response = requestSpecification.contentType(ContentType.JSON)
                .when()
                .get();
        assertActions.verifyStatusCode(response);
        validatableResponse = response.then().statusCode(200).log().all();
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
    }

    @Test(dependsOnMethods = "createNote")
    public void updateNote(ITestContext iTestContext) throws JsonProcessingException{
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.updateNotes+"/"+iTestContext.getAttribute("id"))
                .header("x-auth-token",this.getToken());

        System.out.println(iTestContext.getAttribute("id")+"");
        response = requestSpecification.contentType(ContentType.JSON)
                .body(payloadManager.updateNote(iTestContext.getAttribute("id")+"","Updated Title"))
                .when()
                .put();

        validatableResponse = response.then().log().all();
        iTestContext.setAttribute("title",jsonPath.getString("data.title"));
        iTestContext.setAttribute("description",jsonPath.getString("data.description"));
    }

    @Test(dependsOnMethods = "createNote")
    public void deleteNote(ITestContext iTestContext) throws JsonProcessingException{
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        requestSpecification = RestAssured.given().baseUri(APIConstants.baseURI)
                .basePath(APIConstants.deleteNotes+"/"+iTestContext.getAttribute("id"))
                .header("x-auth-token",this.getToken());

        response = requestSpecification.contentType(ContentType.JSON)
                .when()
                .delete();

        validatableResponse = response.then().statusCode(200);

    }

}
