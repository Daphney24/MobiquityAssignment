# MobiquityAssessment

Tech Stack: Java , RestAssured , TestNG, Maven

**Tasks:**

For this task, imagine you are a part of the team that performs quality assurance for a user blog, the frontend design is not yet developed, but the API has already been published here: https://jsonplaceholder.typicode.com/.
The task is:
1. To create a test automation framework skeleton.
2. To perform the validations for the comments for the post made by a specific user.

**Flow to be tested**

1. Search for the user with username “Delphine”.
2. Use the details fetched to make a search for the posts written by the user.
3. For each post, fetch the comments and validate if the emails in the comment section are in the proper format.
4. Think of various scenarios for the test workflow, all the things that can go wrong. Add them to test coverage

Framework Architecture
--------------
    MobiquityAssessent
	   |
       |_src/main/java
       |  |_org.base.test
       |  |  |_PropertyReader.java   
       |  |_utils
       |  |  |_ExtentReportListener.java
	   |_src/main/resources
       |  |_config.properties
	   |_src/test/java
       |  |_org.api.test
       |  |  |_CoreClass.java
       |  |  |_MobiquityApiTest.java
       |  |  |_TestUtils.java
       |  |_org.model.test
       |  |  |_Comment.java
       |  |  |_Posts.java

   
Building Up the Framework
--------------
	
* Step 1 : Create a maven project
* Step 2 : Add dependencies in pom.xml
* Step 3 : Add testing plugin
* Step 4 : Create tests
* Step 5 : Run and verify

### Installation (pre-requisites)
1. JDK 1.8+ (make sure Java class path is set)
2. Maven (make sure .m2 class path is set)
3. IDE (Eclipse, IntelliJ, etc)
4. TestNG
5. Rest Assured Dependencies
6. Git

Setup Instructions
--------------
Clone the repo from below copmmand or download zip and set it up in your local workspace.
```
git clone https://github.com/Daphney24/MobiquityAssignment.git
```

Verify Installation and Running test
--------------
Use Maven:
	
Go to your project directory from terminal and hit following commands
```
mvn test
```
	
	
### Reporters
	
Once you run the tests the reports will be generated in Extent Reports format to communicate pass/failure.It also includes the default HTML reports as well.

Note: To look at the results in IDE, open the folder test-output/Report/test/ExtentReport.html 
