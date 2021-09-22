package org.api.test;


import java.util.List;

import org.model.test.Comment;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class MobiquityApiTest extends CoreClass {

	private int foundUserId = 9 ;
	private  List<Integer> postIds = null; 


	@Test(priority = 1)
	@Parameters("username")
	public void testUserWithUsername(String username) {
		String userPath = prop.getProperty("api.path.users"); 
		try {
			Response response =  TestUtils.getRequestWithRequestParam(requestSpec,TestUtils.USERID_REQUEST_PARAM, username, userPath);
			foundUserId = (Integer) response.jsonPath().getList("id").get(0);
			Assert.assertTrue(!(foundUserId==999), "No user found with user "+username);
			test.log(LogStatus.PASS, TestUtils.SUCCCESSFULLY_VALIDATED_STATUS_CODE+ response.getStatusCode());
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, TestUtils.TEST_ASSERTION_FAILED_MSG+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,TestUtils.ERROR_MSG +e.fillInStackTrace());
			Assert.fail();
		}							
	}

	@Test(priority = 2)
	public void testPostByUser() {
		String postPath = prop.getProperty("api.path.posts"); 
		try {
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.USERID_REQUEST_PARAM , foundUserId+"", postPath);
			postIds = response.jsonPath().getList("id");
			test.log(LogStatus.PASS, TestUtils.SUCCCESSFULLY_VALIDATED_STATUS_CODE + response.getStatusCode());
		}catch (AssertionError e) {
			test.log(LogStatus.FAIL, TestUtils.TEST_ASSERTION_FAILED_MSG+ e.fillInStackTrace());
			Assert.fail();
		}catch(Exception e) {
			test.log(LogStatus.FAIL,TestUtils.ERROR_MSG +e.fillInStackTrace());
			Assert.fail();
		}		
	}


	@Test(priority = 3)
	public void testCommentEmailFormat() {
		String commentPath = prop.getProperty("api.path.comments");
		List<Comment> commentsForAllPost = TestUtils.getComentsByPost(requestSpec, postIds, commentPath);
		for(Comment comment : commentsForAllPost) {
			boolean checkEmail = TestUtils.isValid(comment.getEmail());
			int postId = comment.getPostId();
			try {
				Assert.assertTrue(checkEmail);
				test.log(LogStatus.PASS, "Email validation Success for comment"+comment.getId()+"Email :: "+comment.getEmail()+" for post id "+postId);				
			}catch (AssertionError e) {
				test.log(LogStatus.FAIL,"Email validation  Failed for comment"+comment.getId()+"Email :: "+comment.getEmail()+" for post id "+postId+"with error"+e.getMessage());
			}
		}
	}





}
