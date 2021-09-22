package org.api.test;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.base.test.PropertyReader;
import org.json.simple.JSONObject;
import org.model.test.Comment;
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

	private  RequestSpecification requestSpec;
	private  Properties prop;

	private int foundUserId = 999 ;
	private  List<Integer> postIds = null; 


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
			Response response =  TestUtils.getRequestWithRequestParam(requestSpec, "username" , username, "/users");
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
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, "userId" , foundUserId+"", "/posts");
			postIds = response.jsonPath().getList("id");
			test.log(LogStatus.PASS, "Succcessfully validated status code:: " + response.getStatusCode());
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, "Test Assertion failed,  logs are:: "+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,"Error thrown is: "+e.fillInStackTrace());
			Assert.fail();
		}		
	}



}
