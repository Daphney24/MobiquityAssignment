package org.api.test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestUtils {
	
	private static final int SUCCESS_STATUS_CODE = 200;

	public static Response getRequestWithRequestParam(RequestSpecification requestSpec,String queryParam ,String username, String path) throws AssertionError {
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
