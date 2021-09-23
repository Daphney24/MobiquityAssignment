package org.api.test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.model.test.Comment;
import org.testng.Assert;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class TestUtils {

	private static final int SUCCESS_STATUS_CODE = 200;
	public static final String USERNAME_REQUEST_PARAM = "username";
	public static final String USERID_REQUEST_PARAM = "userId";
	public static final String POSTID_REQUEST_PARAM = "postId";

	public static final String ERROR_MSG = "Error thrown is: ";
	public static final String TEST_ASSERTION_FAILED_MSG = "Test Assertion failed,  logs are:: ";
	public static final String SUCCCESSFULLY_VALIDATED_STATUS_CODE = "Succcessfully validated status code:: ";
	public static final String EMAIL_CHECK_SUCCESS = "Email check is successfull for post ";
	public static final String EMAIL_CHECK_FAILED = "Email check is successfull for post ";
	
	

	public static Response getRequestWithRequestParam(RequestSpecification requestSpec,String queryParam ,String queryParamValue, String path) throws AssertionError {
		Response response = given().
				spec(requestSpec).
				queryParam(queryParam, queryParamValue).
				and().
				get(path).
				then().
				extract().
				response();
		Assert.assertEquals(SUCCESS_STATUS_CODE, response.getStatusCode());
		return response;
	}

	public  static  List<Comment> getComentsByPost( RequestSpecification requestSpec , List<Integer> postIds, String commentPath) throws AssertionError {
		List<Comment> comments = new ArrayList<Comment>();
		for(int postId : postIds) {
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.POSTID_REQUEST_PARAM, postId+"", commentPath);
			Comment comment[] = response.as(Comment[].class);
			comments.addAll(Arrays.asList(comment));
		}
		return comments;
	}

	public  static boolean isValid(String email) {
		String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	public static Response getRequestWithPathParam(RequestSpecification requestSpec,int pathParamValue ,String path) throws AssertionError {
		Response response = given().
				spec(requestSpec).
				and().
				get(path,pathParamValue).
				then().
				extract().
				response();
		Assert.assertEquals(SUCCESS_STATUS_CODE, response.getStatusCode());
		return response;
	}
}

