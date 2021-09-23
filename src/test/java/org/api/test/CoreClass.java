package org.api.test;

import java.util.Properties;

import org.base.test.PropertyReader;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ExtentReportListener;

@Listeners(ExtentReportListener.class)
public class CoreClass extends ExtentReportListener {

	public RequestSpecification requestSpec;
	public String userPath;
	public String postPath;
	public String commentPath;

	@BeforeTest
	public void setup() {
		PropertyReader propertyReader = new PropertyReader();
		Properties prop = propertyReader.getProp();
		String url = prop.getProperty("api.baseUri");
		userPath = prop.getProperty("api.path.users");
		postPath = prop.getProperty("api.path.posts");
		commentPath = prop.getProperty("api.path.comments");
		requestSpec = new RequestSpecBuilder().setBaseUri(url).setContentType(ContentType.JSON).build();
		test.log(LogStatus.PASS, "Setup is Succcessfully");
	}

	@AfterTest
	public void afterSuite() {
		System.out.println("Execution Completed");
	}

}
