package org.api.test;

import java.util.List;

import org.model.test.Comment;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class MobiquityApiTest extends CoreClass {

	private int foundUserId = -9999;
	private List<Integer> postIds = null;

	@Test(priority = 1)
	@Parameters("username")
	public void testUserWithUsername(String username) {
		try {
			Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.USERNAME_REQUEST_PARAM,
					username, userPath);
			foundUserId = (Integer) response.jsonPath().getList("id").get(0);
			Assert.assertTrue(!(foundUserId == -9999), "No user found with user " + username);
			test.log(LogStatus.PASS, TestUtils.SUCCCESSFULLY_VALIDATED_STATUS_CODE + response.getStatusCode());
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, TestUtils.TEST_ASSERTION_FAILED_MSG + e.fillInStackTrace());
			Assert.fail();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, TestUtils.ERROR_MSG + e.fillInStackTrace());
			Assert.fail();
		}
	}

	@Test(priority = 2)
	public void testEmailFormatForFoundUser() {
		try {
			Response response = TestUtils.getRequestWithPathParam(requestSpec, foundUserId, userPath + "/{userId}");
			String userEmail = response.jsonPath().getString("email");
			boolean checkEmail = TestUtils.isValid(userEmail);
			Assert.assertTrue(checkEmail);
			test.log(LogStatus.PASS,
					"Successfully validate email for user [" + foundUserId + "]  Email :: [" + userEmail + "]");
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, TestUtils.TEST_ASSERTION_FAILED_MSG + e.fillInStackTrace());
			Assert.fail();
		} catch (Exception e) {
			test.log(LogStatus.FAIL, TestUtils.ERROR_MSG + e.fillInStackTrace());
			Assert.fail();
		}
	}

	@Test(priority = 3)
	public void testPostByUser() {
		if (foundUserId != -9999) {
			try {
				Response response = TestUtils.getRequestWithRequestParam(requestSpec, TestUtils.USERID_REQUEST_PARAM,
						foundUserId + "", postPath);
				postIds = response.jsonPath().getList("id");
				test.log(LogStatus.PASS, TestUtils.SUCCCESSFULLY_VALIDATED_STATUS_CODE + response.getStatusCode());
			} catch (AssertionError e) {
				test.log(LogStatus.FAIL, TestUtils.TEST_ASSERTION_FAILED_MSG + e.fillInStackTrace());
				Assert.fail();
			} catch (Exception e) {
				test.log(LogStatus.FAIL, TestUtils.ERROR_MSG + e.fillInStackTrace());
				Assert.fail();
			}
		} else {
			test.log(LogStatus.PASS, "No User found");
		}
	}

	@Test(priority = 4)
	public void testCommentEmailFormat() {
		if (postIds != null) {
			List<Comment> commentsForAllPost = TestUtils.getComentsByPost(requestSpec, postIds, commentPath);
			boolean failChecker = false;
			for (Comment comment : commentsForAllPost) {
				boolean checkEmail = TestUtils.isValid(comment.getEmail());
				int postId = comment.getPostId();
				try {
					Assert.assertTrue(checkEmail);
					test.log(LogStatus.PASS, "Email validation Success for comment [" + comment.getId() + "] Email ["
							+ comment.getEmail() + "] for post id " + postId);
				} catch (AssertionError e) {
					failChecker = true;
					test.log(LogStatus.FAIL, "Email validation  Failed for comment [" + comment.getId() + "] Email ["
							+ comment.getEmail() + "] for post id " + postId + " with error ::" + e.getMessage());
				}
			}

			if (failChecker) {
				Assert.fail();
			}

		} else {
			test.log(LogStatus.PASS, "No posts found");
		}

	}

}