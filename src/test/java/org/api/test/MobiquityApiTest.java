package org.api.test;


import java.util.List;
import java.util.Properties;

import org.base.test.PropertyReader;
import org.model.test.Comment;
import org.testng.Assert;
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
		String userPath = prop.getProperty("api.path.users"); 
		try {
			Response response =  TestUtils.getRequestWithRequestParam(requestSpec,TestUtils.USERID_REQUEST_PARAM, username, userPath);
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
		String postPath = prop.getProperty("api.path.posts"); 
		try {
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.USERID_REQUEST_PARAM , foundUserId+"", postPath);
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


	@Test(priority = 3)
	public void testCommentEmailFormat() {
		String commentPath = prop.getProperty("api.path.comments"); 
		for(int postId : postIds) {
			System.out.println("  ---- "+postId);
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.POSTID_REQUEST_PARAM, postId+"", commentPath);
			response.prettyPrint();
			Comment[] comment = response.as(Comment[].class);
			System.out.println(comment);
		}

	}



}
