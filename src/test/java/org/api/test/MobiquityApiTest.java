package org.api.test;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
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
	
	private List<Integer> foundUserIds = null;
	
	
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
	
	@Test
	@Parameters("username")
	public void testUserWithUsername(String username) {
		try {
			Response response = given().
					spec(requestSpec).
					queryParam("username", username).
					and().
					get("/users").
					then().
					extract().
					response();
			
			Assert.assertEquals(SUCCESS_STATUS_CODE, response.getStatusCode());
			foundUserIds = response.jsonPath().getList("id");
			System.out.println(foundUserIds);
			Assert.assertTrue(foundUserIds.size()>0, "No user found with user "+username);
			test.log(LogStatus.PASS, "Succcessfully validated status code:: " + response.getStatusCode() +" and bookingids");
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, "Test Assertion failed,  logs are:: "+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,"Error thrown is: "+e.fillInStackTrace());
			Assert.fail();
		}			
		
		
	}

	
}
