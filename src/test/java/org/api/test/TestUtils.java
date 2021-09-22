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

	public  static  List<Comment> getComentsByPost( RequestSpecification requestSpec , List<Integer> postIds, String commentPath) throws AssertionError {
		Comment comment[] = null;
		List<Comment> comments = new ArrayList<Comment>();
		for(int postId : postIds) {
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.POSTID_REQUEST_PARAM, postId+"", commentPath);
			comment = response.as(Comment[].class);
			comments.addAll(Arrays.asList(comment));
		}
		return comments;
	}
	
	public  static boolean isValid(String email) {
		String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
}

