package org.api.test;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.base.test.PropertyReader;
import org.json.simple.JSONObject;
import org.model.test.UsersGeo;
import org.model.test.User;
import org.model.test.UsersAddress;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class MobiquityApiTest extends ExtentReportListener{

	private static RequestSpecification requestSpec;
	private static Properties prop;

	private static final int SUCCESS_STATUS_CODE = 200;
	private static final int CREATED_STATUS_CODE = 201;

	private int foundUserId = 999 ;
	private final List<Integer> postIds = null; 


	@BeforeTest
	public void setup()  {
		PropertyReader propertyReader = new PropertyReader();
		prop = propertyReader.getProp();
		String url = prop.getProperty("api.baseUri");
		requestSpec = new RequestSpecBuilder()
				.setBaseUri(url)
				.setContentType(ContentType.JSON)
				.build();	
		test.log(LogStatus.PASS, "Setup is Succcessfully");
	}

	@Test(priority = 1)
	@Parameters("username")
	public void testUserWithUsername(String username) {
		try {
			Response response = getRequestWithRequestParam( "username" , username, "/users");
			foundUserId = (Integer) response.jsonPath().getList("id").get(0);
			Assert.assertTrue(!(foundUserId==999), "No user found with user "+username);
			test.log(LogStatus.PASS, "Succcessfully validated status code:: " + response.getStatusCode());
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, "Test Assertion failed,  logs are:: "+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,"Error thrown is: "+e.fillInStackTrace());
			Assert.fail();
		}							
	}

	@Test(priority = 2)
	public void testPostByUser() {
		try {
			Response response = getRequestWithRequestParam( "userId" , foundUserId+"", "/posts");
			List<Integer> postIds = response.jsonPath().getList("id");
			System.out.println(postIds);
			test.log(LogStatus.PASS, "Succcessfully validated status code:: " + response.getStatusCode());
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, "Test Assertion failed,  logs are:: "+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,"Error thrown is: "+e.fillInStackTrace());
			Assert.fail();
		}		

	}

	private Response getRequestWithRequestParam(String queryParam ,String username, String path) throws AssertionError {
		Response response = given().
				spec(requestSpec).
				queryParam(queryParam, username).
				and().
				get(path).
				then().
				extract().
				response();
		Assert.assertEquals(SUCCESS_STATUS_CODE, response.getStatusCode());
		return response;
	}


}
