package stepdefinitions.stepdefinitionsapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import main.utilities.utilitiesapi.ConfigLoader;
import main.utilities.utilitiesapi.LoggerUtil;
import main.utilities.utilitiesapi.TestContext;
import main.utilities.utilitiesapi.ValidationUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.google.gson.Gson;
import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import pages.ApiBase.BaseApi;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class APIStepDefinitions {

    private WebDriver driver;
    private static final TestContext testContext = TestContext.getInstance();
    private static String authorizationToken;
    private static String createdGroupId;
    private static String createdEventTemplateId;
    private final BaseApi baseApi = new BaseApi("https://nhimumobll.execute-api.us-west-2.amazonaws.com/development");
    private Response response;
    public String idValue;
    public String FilePath = "src/test/java/main/utilities/utilitiesapi/FileUtils/SuccessClips";
    public String File2dPath = "src/test/java/main/utilities/utilitiesapi/FileUtils/success2dclips";
    private double firstApiValue;
    private static final String bearer11Token = ConfigLoader.getProperty("user1.token");
    private static final String bearer2Token = ConfigLoader.getProperty("user2.token");
    private static final String user2Id = ConfigLoader.getProperty("user2.ID");
    private static final String Gname = ConfigLoader.getProperty("group.name");
    private static final String Gdescription = ConfigLoader.getProperty("group.description");
    private static final String Gpublic = ConfigLoader.getProperty("isPublic");
    private static final String Gprofilepicture = ConfigLoader.getProperty("profilePicture");
    private static final String Gcount = ConfigLoader.getProperty("membercount");
    private static final Logger logger = Logger.getLogger(APIStepDefinitions.class.getName());
    public static String GROUP_ID = "02ea7466-bb14-4d42-b08c-96cb5e0872c3";
    public static String IN_GROUP_ID = "18cd1-9fa0-45fe-b679-bfcb837c1d6c";
    public static final String FacilitateUserId = "fab188af-0f87-4e7e-a956-cadd2c311dbb";
    public static final String FacilitateGroupId = "60fa1757-b89b-43f3-ae3a-bd6647e399fa";
    public static String FirstGroupId;
    public static String FirstUserId;

    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    private String bearer1Token() {
        return TestContext.getInstance().getAuthorizationToken();
    }

    @Given("I open the login page")
    public void openLoginPage() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); // Use the global instance
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://development.upliftlabs.io/user/login");
        driver.manage().window().maximize();
        Thread.sleep(15000); // Wait for the page to load (reduce or remove for better performance)
    }

    public void closeLoginPage() throws InterruptedException {
        driver.manage().window().maximize();
        Thread.sleep(5000);
        driver.quit();
    }


    @When("I login with username {string} and password {string}")
    public void login(String username, String password) {
        WebElement emailField = driver.findElement(By.xpath("//INPUT[@name='email']"));
        WebElement passwordField = driver.findElement(By.xpath("//INPUT[@name='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//BUTTON[@type='submit']"));

        emailField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    @Then("I navigate to the account page and fetch the bearer token")
    public void navigateToAccountPageAndFetchToken() {
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            String url = request.getRequest().getUrl();
            if (url.contains("/train_development/me") && authorizationToken == null) {
                Map<String, Object> headers = request.getRequest().getHeaders();
                if (headers.containsKey("Authorization")) {
                    authorizationToken = (String) headers.get("Authorization");
                    testContext.setAuthorizationToken(authorizationToken);
                    System.out.println("Authorization token captured: " + authorizationToken);
                }
            }
        });

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//DIV[@class='align-self-center list-thumbnail-letters   inline-flex user-avatar-small  rounded-circle small']")));
        dropdown.click();

        WebElement accountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//BUTTON[@type='button'][text()='Account']")));
        accountButton.click();

        if (authorizationToken != null) {
            driver.manage().window().minimize();
        } else {
            System.err.println("Authorization token not captured.");
            driver.quit();
        }
    }


    @Then("I should receive a response with status code {int}")
    public void validateStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
        ValidationUtils.validateJsonBody(response);
        ValidationUtils.validateResponseTime(response, 3000);
    }

    @When("I send a GET request to groups")
    public void i_send_a_GET_request_to() {
        response = baseApi.getRequest("groups", bearer1Token(), null, null, null, null, null);
    }

    @Then("The response should not contain body")
    public void the_response_should_not_contain_body() {
        Assert.assertTrue(response.getBody().asString().isEmpty(), "The response body is not empty");
    }

    @When("I send a GET request to groups with invalid token")
    public void sendGetRequestWithInvalidToken() {
        response = baseApi.getRequest("groups", ConfigLoader.getProperty("invalid.token"), null, null, null, null, null);
    }

    @When("I send a GET request to groups with a timeout of 1000 milliseconds")
    public void sendGetRequestWithTimeout() {
        response = baseApi.getRequest("groups", bearer1Token(), null, null, 3000, null, null);

    }


    @When("I send a GET request to groups expecting {string} format")
    public void sendGetRequestWithExpectedFormat(String format) {
        response = baseApi.getRequest("groups", bearer1Token(), null, null, null, null, null);
    }

    @When("I send repeated GET requests to groups to test caching")
    public void sendRepeatedGetRequestsToTestCaching() {
        for (int i = 0; i < 5; i++) {
            response = baseApi.getRequest("groups", bearer1Token(), null, null, null, null, null);
        }
    }

    @When("I send a GET request to {string} with Accept header {string}")
    public void sendGetRequestWithAcceptHeader(String endpoint, String acceptHeader) {
        response = baseApi.getRequest("groups", bearer1Token(), null, null, null, null, null);
    }

    @When("I send a GET request to a non-existent endpoint ours")
    public void sendGetRequestToNonExistentEndpoint() {
        response = baseApi.getRequest("ours", bearer1Token(), null, null, null, null, null);
        Assert.assertEquals(response.getStatusCode(), 403);
    }

//    @When("I send a GET request to {string} with If-Modified-Since {string} and If-None-Match {string}")
//    public void sendGetRequestWithConditions(String endpoint, String ifModifiedSince, String ifNoneMatch) {
//        response = apiClient.getRequestWithCondition(endpoint, null,bearer1Token, ifModifiedSince, ifNoneMatch);
//    }

    @When("I send a GET request to groups with page {int}, limit {int}, sortBy {string}")
    public void sendGetRequestWithSortingAndFiltering(int page, int limit, String sortBy) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);
        queryParams.put("sortBy", sortBy);
        response = baseApi.getRequest("/groups", bearer1Token(), queryParams, null, null, null, null);
    }

    @When("I send a GET request to groups with page {int}, limit {int}, invalid bearer token")
    public void sendGetRequestWithInvalidBearerToken(int page, int limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);
        response = baseApi.getRequest("groups", ConfigLoader.getProperty("invalid.token"), queryParams, null, null, null, null);
    }

    @When("I send a GET request to groups with page {int}, limit {int}")
    public void sendGetRequestForGroupsWithPageNumberAndLimit(int page, int limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);
        response = baseApi.getRequest("groups", bearer1Token(), queryParams, null, null, null, null);
        JSONObject Object = new JSONObject(response.asString());
        JSONArray groupDetails = Object.getJSONArray("groups");
        int count = groupDetails.length();
        System.out.println("User part of total number of groups " + count);
    }

//    @When("I send a GET request to {string} with response format {string}")
//    public void sendGetRequestWithResponseFormat(String endpoint, String format) {
//        response = apiClient.getRequest(endpoint, null, format);
//    }

//    @When("I send a GET request to {string} with {string} header set to {string}")
//    public void sendGetRequestWithConditionalHeader(String endpoint, String headerName, String headerValue) {
//        response = apiClient.getRequestWithHeader(endpoint, headerName, headerValue,bearer1Token);
//    }

    @When("I send a POST request to create group with name {string}, description {string}, isPublic {string}, profilePicture {string} without bearer token")
    public void sendPostRequestToCreateGroupWithoutBearerToken(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", false); // Convert isPublic to a boolean
        requestBody.put("profilePicture", profilePicture);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("groups", jsonBody, ConfigLoader.getProperty("empty.token"));
    }

    @When("I send a POST request to create group with name {string}, description {string}, isPublic {string}, profilePicture {string}")
    public void iSendAPOSTRequestToCreateGroupWithNameDescriptionIsPublicProfilePicture(String arg0, String arg1, String arg2, String arg3) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", arg0);
        requestBody.put("description", arg1);
        requestBody.put("isPublic", arg2); // Convert isPublic to a boolean
        requestBody.put("profilePicture", arg3);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("groups", jsonBody, ConfigLoader.getProperty("empty.token"));
    }

    @When("I send a POST request to create group with malformed data")
    public void sendPostRequestWithMalformedData() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", 123);
        requestBody.put("description", true);
        requestBody.put("isPublic", false);
        requestBody.put("profilePicture", 456);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("groups", jsonBody, bearer1Token());
    }

    @When("I send a POST request to create group with long name and description")
    public void sendPostRequestWithLongData() {
        Map<String, Object> requestBody = new HashMap<>();
        String longName = "A".repeat(1000);
        String longDescription = "B".repeat(5000);
        requestBody.put("name", longName);
        requestBody.put("description", longDescription);
        requestBody.put("isPublic", false);
        requestBody.put("profilePicture", "");
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("groups", jsonBody, bearer1Token());
    }


    @When("I send a POST request to create group with invalid JSON")
    public void sendPostRequestWithInvalidJson() {
        String invalidJson = "{\"name\": \"NewGroup\", \"description\": \"Test\", \"isPublic\": \"true\", \"profilePicture\": \"}";
        response = baseApi.postRequest("groups", invalidJson, bearer1Token());
    }

    @When("I send a POST request to create group with unsupported media type")
    public void sendPostRequestWithUnsupportedMediaType() {
        String requestBody = "{\"name\": \"NewGroup\", \"description\": \"Test\", \"isPublic\": \"true\", \"profilePicture\": \"\"}";
        response = baseApi.postRequest("groups", requestBody, bearer1Token());
    }

    @When("I send a POST request to create group with empty body")
    public void sendPostRequestWithEmptyBody() {
        response = baseApi.postRequest("groups", "", bearer1Token());
    }

    @When("I send a POST request to create group with extremely large request body")
    public void sendPostRequestWithExtremelyLargeRequestBody() {
        StringBuilder largeRequestBody = new StringBuilder("{\"name\": \"NewGroup\", \"description\": \"");
        for (int i = 0; i < 10; i++) {
            largeRequestBody.append("large_body_data");
        }
        largeRequestBody.append("\", \"isPublic\": \"true\", \"profilePicture\": \"\"}");
        String jsonBody = new Gson().toJson(largeRequestBody);
        response = baseApi.postRequest("groups", jsonBody, bearer1Token());
    }

    @When("I send a PUT request to update group with name {string}, description {string}, isPublic {string}, profilePicture {string}")
    public void sendPutRequestToUpdateGroup(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", isPublic);
        requestBody.put("profilePicture", profilePicture);
        response = baseApi.putRequest("groups/1", requestBody.toString(), bearer1Token());
    }

    @When("I send a PUT request to update group with name {string}, description {string}, isPublic {string}, profilePicture {string} without bearer token")
    public void sendPutRequestToUpdateGroupWithoutBearerToken(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", isPublic);
        requestBody.put("profilePicture", profilePicture);
        response = baseApi.putRequest("groups/1", requestBody.toString(), null);
    }


    @And("The response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String expectedErrorMessage) {
        response.then()
                .assertThat()
                .body("message", Matchers.equalTo(expectedErrorMessage));
    }

    @When("I send a PUT request to update group with malformed data")
    public void sendPutRequestWithMalformedData() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", 123);
        requestBody.put("description", true);
        requestBody.put("isPublic", "not_a_boolean");
        requestBody.put("profilePicture", 456);
        response = baseApi.putRequest("groups/1", requestBody.toString(), bearer1Token());
    }

    @When("I send a PUT request to update group with long name and description")
    public void sendPutRequestWithLongData() {
        String longName = "A".repeat(1000);
        String longDescription = "B".repeat(5000);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", longName);
        requestBody.put("description", longDescription);
        requestBody.put("isPublic", "true");
        requestBody.put("profilePicture", "");
        response = baseApi.putRequest("groups/1", requestBody.toString(), bearer1Token());
    }

    @When("I send a PUT request to update group with non-existent ID")
    public void sendPutRequestWithNonExistentId() {
        String requestBody = "{\"name\": \"UpdatedGroup\", \"description\": \"Updated\", \"isPublic\": \"true\", \"profilePicture\": \"\"}";
        response = baseApi.putRequest("groups/9999", requestBody, bearer1Token());
    }

    @When("I send a PUT request to update group with invalid JSON")
    public void sendPutRequestWithInvalidJson() {
        String invalidJson = "{\"name\": \"UpdatedGroup\", \"description\": \"Updated\", \"isPublic\": \"true\", \"profilePicture\": \"";
        response = baseApi.putRequest("groups/1", invalidJson, bearer1Token());
    }

    @When("I send a PUT request to update group with partial update")
    public void sendPutRequestWithPartialUpdate() {
        String requestBody = "{\"description\": \"Updated\"}";
        response = baseApi.putRequest("groups/1", requestBody, bearer1Token());
    }

    @When("I send a PUT request to update group with missing required fields")
    public void sendPutRequestWithMissingRequiredFields() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "");
        requestBody.put("description", "");
        response = baseApi.putRequest("groups/update", requestBody.toString(), bearer1Token());
    }

    @When("I send a DELETE request to delete group with invalid groupID")
    public void sendDeleteRequestWithInvalidID() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", IN_GROUP_ID);
        response = baseApi.deleteRequest("groups", queryParams, bearer1Token());
    }

    @When("I send a DELETE request to delete group with non-existent ID")
    public void sendDeleteRequestWithNonExistentID() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", IN_GROUP_ID);
        response = baseApi.deleteRequest("groups", queryParams, bearer1Token());
    }

    @When("I send a DELETE request to delete group with valid ID")
    public void sendDeleteRequestWithValidID() {
//        Map<String, Object>  queryParams= new HashMap<>();
//        queryParams.put("groupId", GROUP_ID);
        response = baseApi.deleteRequest("groups/" + GROUP_ID, null, bearer1Token()); // Assuming ID 1 for demo
    }

    @When("I send a DELETE request to delete group with valid ID without bearer token")
    public void sendDeleteRequestWithValidIdWithoutBearerToken() {
        response = baseApi.deleteRequest("groups", null, ConfigLoader.getProperty("invalid.token"));
    }

    @When("I send a DELETE request to delete group with valid ID that has already been deleted")
    public void sendDeleteRequestWithValidIdThatHasAlreadyBeenDeleted() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", IN_GROUP_ID);
        response = baseApi.deleteRequest("groups", queryParams, bearer1Token());
    }

    @When("I send a DELETE request to delete group with malformed ID")
    public void sendDeleteRequestWithMalformedId() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", IN_GROUP_ID);
        response = baseApi.deleteRequest("groups", queryParams, bearer1Token());
    }

    @Then("The response should contain error messages {string}")
    public void validateErrorMessage(String expectedErrorMessage) {
        response.then()
                .assertThat()
                .body("message", Matchers.equalTo(expectedErrorMessage));
    }

    @Then("The response should contain error message with type {string}")
    public void validateErrorMessageWithType(String expectedType) {
        response.then()
                .assertThat()
                .body("type", Matchers.equalTo(expectedType));
    }

    @Then("The response should contain the updated group details")
    public void responseShouldContainUpdatedGroupDetails() {
        JsonPath jsonResponse = response.jsonPath();
        String expectedName = "UpdatedGroup";
        String expectedDescription = "Updated";
        boolean expectedIsPublic = true;
        String expectedProfilePicture = "";
        Assert.assertEquals(((JsonPath) jsonResponse).getString("name"), expectedName, "Group name mismatch");
        Assert.assertEquals(jsonResponse.getString("description"), expectedDescription, "Group description mismatch");
        Assert.assertEquals(jsonResponse.getBoolean("isPublic"), expectedIsPublic, "Group visibility mismatch");
        Assert.assertEquals(jsonResponse.getString("profilePicture"), expectedProfilePicture, "Group profile picture mismatch");
    }

    @When("I send a POST request to {string} with body {string}")
    public void i_send_a_POST_request_to_with_body(String endpoint, String body) {
        response = baseApi.postRequest(endpoint, body, bearer1Token());
    }

    @When("I send a PUT request to {string} with body {string}")
    public void i_send_a_PUT_request_to_with_body(String endpoint, String body) {
        response = baseApi.putRequest(endpoint, body, bearer1Token());
    }

    @When("I send a DELETE request to delete group invite with ID {string}")
    public void i_send_a_DELETE_request_to_delete_group_invite_with_id(String inviteId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", GROUP_ID);
        response = baseApi.deleteRequest(inviteId, queryParams, bearer1Token());
    }

    @When("I validate the response status code is {int}")
    public void i_validate_the_response_status_code_is(int expectedStatusCode) {
        Assert.assertEquals("Invalid response status code", expectedStatusCode, String.valueOf(response.getStatusCode()));
    }

    @When("I log the response for debugging")
    public void i_log_the_response_for_debugging() {
        LoggerUtil.logResponse(response);
    }

    @When("I send a GET request to search users within a group with valid groupId and query")
    public void sendGetRequestToSearchUsersWithinGroup(String groupId, String query) {
        String endpoint = String.format("groups/%s/users?search=%s", groupId, query);
        response = baseApi.getRequest(endpoint, bearer1Token(), null, null, null, null, null);
    }

    @When("I send a DELETE request to delete an accepted group invite with valid inviteId")
    public void sendDeleteRequestToDeleteAcceptedGroupInvite(String endpoint) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("groupId", GROUP_ID);
        response = baseApi.deleteRequest(endpoint, queryParams, bearer1Token());
    }

    @When("I send a DELETE request to delete a declined group invite with valid inviteId")
    public void sendDeleteRequestToDeleteDeclinedGroupInvite() {
        String endpoint = "/groups/:" + IN_GROUP_ID + "/decline-invite";
        response = baseApi.deleteRequest(endpoint, null, bearer1Token());
    }

    @When("I send a POST request to create group")
    public void createGroup() {

        if (bearer1Token() == null) {
            throw new IllegalStateException("Authorization token not set. Please ensure the login scenario runs first.");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", Gname);
        requestBody.put("description", Gdescription);
        requestBody.put("isPublic", Gpublic);
        requestBody.put("profilePicture", Gprofilepicture);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("groups", jsonBody, bearer1Token());
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
        response.prettyPrint();
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        String groupId = responseBody.getString("groupId");

        assertThat("Group ID should not be null.", groupId, notNullValue());
    }

    @When("User 1 fetches the group ID of the newly created group")
    public void fetchGroupId() {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        createdGroupId = responseBody.getString("groupId");
        System.out.println("==========================" + createdGroupId + "==============================");
        //Assert.assertNotNull(createdGroupId, "Group ID should not be null.");
        LoggerUtil.log("Fetched Group ID: " + createdGroupId);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 sends a group invite to User 2 with userId")
    public void sendGroupInvite() {
        response = baseApi.sendGroupInvite(createdGroupId, user2Id, bearer1Token());
        ValidationUtils.validateJsonBody(response);
        String inviteId = response.jsonPath().getString("inviteId");
        LoggerUtil.log("Invite sent with Invite ID: " + inviteId);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @When("User 2 accepts the group invite")
    public void acceptGroupInvite() {
        String endpoint = "/groups/" + createdGroupId + "/accept-invite";
        response = baseApi.deleteRequest(endpoint, null, bearer2Token);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 fetches the group details to verify the member count is 2")
    public void verifyMemberCount() {
        response = baseApi.getRequest("/groups", bearer1Token(), null, null, null, null, createdGroupId);
        String memberCount = response.jsonPath().getString("memberCount");
        Assert.assertEquals(memberCount, Gcount, "Member count does not match.");
        LoggerUtil.log("Verified member count is: " + memberCount);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 lists the group users to verify that User 2 is a member of the group")
    public void listGroupUsers() {
        response = baseApi.getRequest("/groups/" + createdGroupId + "/users", bearer1Token(), null, null, null, null, null);
        JsonPath jsonPath = response.jsonPath();
        List<String> userIds = jsonPath.getList("members.userId");
        if (userIds == null) {
            LoggerUtil.log("The 'members' field is missing or null. The response might be incorrect.");
            Assert.fail("The 'members' field is missing or null. The response might be incorrect.");
        } else {
            boolean isUserPresent = userIds.contains(user2Id);
            Assert.assertTrue(isUserPresent, "User 2 is not a member of the group.");
            LoggerUtil.log("Verified that User 2 with userId " + user2Id + " is a member of the group.");
        }
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @When("I send a POST request to create Room Event template")
    public void iSendAPOSTRequestToCreateRoomEventID() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("assessmentProtocolId", 1);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("room-event-templates", jsonBody, bearer1Token());
        ValidationUtils.validateJsonBody(response);
        response.prettyPrint();
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        String roomeventID = responseBody.getString("roomEventTemplateId");
        System.out.println(roomeventID);
    }

    @When("User 1 fetches the Room Event ID of the newly created Template")
    public void userFetchesTheRoomEventIDOfTheNewlyCreatedTemplate() {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        createdEventTemplateId = responseBody.getString("roomEventTemplateId");
        System.out.println("===============Template id : " + createdEventTemplateId + "==============================");
        //Assert.assertNotNull(createdGroupId, "Group ID should not be null.");
        LoggerUtil.log("Fetched Group ID: " + createdEventTemplateId);
        //ValidationUtils.validateJsonBody(response);

    }

    @When("User 1 creates the assessment based on the Room Event template ID")
    public void userCreatesTheAssessmentBasedOnTheRoomEventID() {
        response = baseApi.postRequest("room-events/assessments/" + createdEventTemplateId, null, bearer1Token());
        ValidationUtils.validateJsonBody(response);
        // response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idValue = jsonPath.getString("roomEvent.id");
        System.out.println("======================idValue : " + idValue + "======================");
    }

    @Then("^user processes all txt files and creates assets$")
    public void userProcessesAllTxtFilesAndCreatesAssets() throws IOException {
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId");
        String directoryPath = FilePath; // Update with actual path
        processTxtFilesAndCreateAssets(directoryPath, includeGroupId);
    }


    private Map<String, String> loadAssetIdsFromProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        }

        Map<String, String> assetIds = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            assetIds.put(name, properties.getProperty(name));
        }
        return assetIds;
    }


    public void processTxtFilesAndCreateAssets(String directoryPath, boolean includeGroupId) throws IOException {
        File folder = new File(directoryPath);
        File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        Properties assetIdsProperties = new Properties();
        if (txtFiles != null) {
            for (File txtFile : txtFiles) {
                String assetId = UUID.randomUUID().toString();
                logger.info("------------------------------------------------------");
                logger.info(assetId);
                logger.info("------------------------------------------------------");
                createAsset(assetId, includeGroupId);
                assetIdsProperties.put(txtFile.getName(), assetId);
            }
        }
        try (FileOutputStream output = new FileOutputStream("src/test/java/main/utilities/utilitiesapi/FileUtils/InputFolder/assetIds.properties")) { // Update with actual path
            assetIdsProperties.store(output, "Asset IDs");
        }
    }


    public void createAsset(String assetId, boolean includeGroupId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue);
        List<String> assetTags = Collections.singletonList("user_upload");
        requestBody.put("assetTag", assetTags);
        requestBody.put("assetId", assetId);

        if (includeGroupId) {
            requestBody.put("groupId", FacilitateGroupId);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBody = gson.toJson(requestBody);

        // Assuming apiClient and bearer1Token are available and properly configured
        response = baseApi.postRequest("assets", jsonBody, bearer1Token());
    }

    @Then("user processes all txt files and creates metrics")
    public void userProcessesAllTxtFiles() throws IOException {
        String directoryPath = FilePath;
        String directory2dPath = File2dPath; // Path for 2D data files
        Map<String, String> assetIds = loadAssetIdsFromProperties("src/test/java/main/utilities/utilitiesapi/FileUtils/InputFolder/assetIds.properties");
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue);
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId"); // Check if groupId should be included
        if (includeGroupId) {
            requestBody.put("groupId", FacilitateGroupId);
            requestBody.put("userId", FacilitateUserId);
        }
        requestBody.put("mock", false);
        List<Map<String, Object>> captures = new ArrayList<>();

        Files.list(Paths.get(directoryPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(path -> {
                    try {
                        // Read data from the current file
                        String data = new String(Files.readAllBytes(path));
                        data = data.replaceAll("\\\\n(?=\\d)", "\n");
                        data = data.replaceAll("\\\\n", "\n");

                        // Fetch corresponding 2D data
                        String fileName = path.getFileName().toString();
                        Path data2dPath = Paths.get(directory2dPath, fileName); // Match file name in 2D folder
                        String data2d = "";
                        if (Files.exists(data2dPath)) {
                            data2d = new String(Files.readAllBytes(data2dPath));
                            data2d = data2d.replaceAll("\\\\n(?=\\d)", "\n");
                            data2d = data2d.replaceAll("\\\\n", "\n");
                        }

                        // Process file name to extract dimensions
                        String[] fileNameParts = fileName.split("_", 3);
                        String dimensionKey = fileNameParts[0] + "_" + fileNameParts[1];
                        String dimensionValue = fileNameParts[2].replace(".txt", "");
                        if (dimensionValue.matches(".*-\\d+$")) {
                            dimensionValue = dimensionValue.replaceFirst("-\\d+$", "");
                        }
                        Map<String, String> dimension = new HashMap<>();
                        dimension.put(dimensionKey, dimensionValue);

                        // Create capture object
                        String assetId = assetIds.get(fileName);
                        Map<String, Object> capture = new HashMap<>();

                        capture.put("assetId", assetId);
                        capture.put("dimension", Collections.singletonList(dimension));
                        capture.put("keypointModel", "Keypoints_3D-mobile_ios-general-pose_3d-videopose-coco_wholebody:v0");
                        capture.put("data", data); // Add original data
                        capture.put("data2d", data2d); // Add 2D data
                        capture.put("movement", "countermovement");
                        capture.put("activity", "jump");
                        capture.put("framerate", 211.91908264160156);
                        long epochTimestamp = System.currentTimeMillis() / 1000;
                        capture.put("capturedAt", epochTimestamp);

                        // Add to captures list
                        captures.add(capture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Build request body and save JSON
        requestBody.put("captures", captures);
        String jsonBody = new Gson().toJson(requestBody);
        System.out.println("Generated JSON: " + jsonBody);

        try (FileWriter fileWriter = new FileWriter("src/test/resources/captures.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(captures, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send API request
        response = baseApi.postRequest("/performance/metrics", jsonBody, bearer1Token());
        System.out.println(response);
    }


    @When("I send a POST request to create aggregate metrics")
    public void iSendAPOSTRequestToCreateAggregateMetrics() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue);
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("mock", false);
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId"); // Check if groupId should be included
        if (includeGroupId) {
            requestBody.put("groupId", FacilitateGroupId);
            requestBody.put("userId", FacilitateUserId);
        }
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("performance/aggregate", jsonBody, bearer1Token());
        ValidationUtils.validateJsonBody(response);

//        JSONObject responseBody = new JSONObject(response.getBody().asString());
//        String roomeventID = responseBody.getString("roomEventTemplateId");
//        System.out.println(roomeventID);
    }

    @When("i send performance overview call to get insights data")
    public void iSendPerformanceOverviewCall() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("timezone", "Asia/Kolkata");
        response = baseApi.getRequest("performance/overview", bearer1Token(), queryParams, null, null, null, null);
        // Parse the response and extract the "value" attribute from the first item
        JSONArray responseBody = new JSONArray(response.getBody().asString());
        JSONObject firstMetric = responseBody.getJSONObject(0);
        this.firstApiValue = firstMetric.getDouble("value");
    }

    @Then("I send a Get call to get the leaderboard value and verify with insights value")
    public void iSendAGetCallToGetTheLeaderboard() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("groupId", "0ac5bcad-ed0d-4cc4-96e7-0d8b3414f139");
        requestBody.put("interval", "weekly");
        requestBody.put("timezone", "America/Los_Angeles");
        response = baseApi.getRequest("leaderboards", bearer1Token(), requestBody, null, null, null, null);
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        JSONArray leaderboard = responseBody.getJSONArray("leaderboard");
        JSONObject firstEntry = leaderboard.getJSONObject(0);
        double leaderboardValue = firstEntry.getDouble("value");
        if (this.firstApiValue == leaderboardValue) {
            System.out.println("Values match: " + this.firstApiValue + " == " + leaderboardValue);
        } else {
            System.out.println("Values do not match: " + this.firstApiValue + " != " + leaderboardValue);
        }
    }

    @When("I send performance overview call with range {string} and metricId {string}")
    public void iSendPerformanceOverviewCallToGetDetailedInsightsData(String rangeSelection, String metricIdSelection) {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate;
        switch (rangeSelection) {
            case "7days":
                startDate = endDate.minusDays(7);
                break;
            case "30days":
                startDate = endDate.minusDays(30);
                break;
            case "12months":
                startDate = endDate.minusMonths(12);
                break;
            default:
                throw new IllegalArgumentException("Invalid range selection: " + rangeSelection);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue); // Assuming idValue is defined and set elsewhere in the class
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("mock", "false");
        requestBody.put("dimensionIds", "1,2");
        requestBody.put("granularity", "day");
        requestBody.put("uplift-app", "takeoff");
        requestBody.put("uplift-version", "2");
        requestBody.put("startDate", formattedStartDate);
        requestBody.put("endDate", formattedEndDate);
        requestBody.put("metricId", metricIdSelection);

        // Add groupId to the requestBody only if it's required
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId"); // Check if groupId should be included
        if (includeGroupId) {
            requestBody.put("groupId", FacilitateGroupId);
            requestBody.put("userId", FacilitateUserId);
        }

        response = baseApi.getRequest("performance/aggregate", bearer1Token(), requestBody, null, null, null, null);
    }


    @When("i send get call to get the list of past room events")
    public void iSendGetCallToGetTheListOfPastRoomEvents() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("page", 0); // Assuming idValue is defined somewhere in the class
        requestBody.put("limit", "50");
        requestBody.put("classType", "private");
        response = baseApi.getRequest("room-events", bearer1Token(), requestBody, null, null, null, null);

        // Parse the JSON response
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree((JsonParser) response);

            // Assuming "roomEvents" is an array
            JsonNode roomEvents = rootNode.get("roomEvents");
            if (roomEvents.isArray()) {
                for (JsonNode event : roomEvents) {
                    String eventId = event.get("id").asText();
                    // Store eventId somewhere (e.g., in a list or database)
                    // You can also perform other actions with the eventId
                }
            } else {
                System.out.println("No room events found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Then("i delete all the room events using post call")
    public void iDeleteAllTheRoomEventsUsingPostCall() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("roomEventId", "43f40fa6-809f-4e5d-91b3-2464d9278a79");
        queryParams.put("groupId", "string");

        response = baseApi.deleteRequest("/room-events/:", queryParams, bearer1Token());

    }

    @Then("i verify if there are any room events present for it")
    public void iVerifyIfThereAreAnyRoomEventsPresentForIt() {
    }


    @When("i send performance overview call to get detailed insights data")
    public void iSendPerformanceOverviewCallToGetDetailedInsightsData() {
    }

    @When("i send get request to get the my profile")
    public void iSendGetRequestToGetTheMyProfile() {
        response = baseApi.getRequest("me", bearer1Token(), null, null, null, null, null);
        String id = response.asString();
    }

    @When("I send Post request to create Assessment without roomevent template")
    public void iSendPostRequestToCreateAssessment() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("assessmentProtocolId", 1);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("room-events/assessments", jsonBody, bearer1Token());

    }


    @When("I send a GET request to groups with page {int}, limit {int}, role {string}")
    public void sendGetRequestForGroupsWithPageNumberAndLimit(int page, int limit, String role) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);
        queryParams.put("role", role);
        response = baseApi.getRequest("groups", bearer1Token(), queryParams, null, null, null, null);
        List<String> groupIds = extractGroupIds(response);
        TestContext.setGroupIds(groupIds);
        FirstGroupId = groupIds.get(0);
    }

    private List<String> extractGroupIds(Response response) {
        List<String> groupIds = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.asString());
        JSONArray groups = jsonResponse.getJSONArray("groups");
        for (int i = 0; i < groups.length(); i++) {
            groupIds.add(groups.getJSONObject(i).getString("groupId"));
        }
        return groupIds;
    }

    @Then("I send a DELETE request to delete all group")
    public void iSendADELETERequestToDeleteAllGroup() {
        List<String> groupIds = TestContext.getGroupIds();
        for (String groupId : groupIds) {
            deleteGroup(groupId);
        }
    }

    private void deleteGroup(String groupId) {
        response = baseApi.deleteRequest("groups/" + groupId, null, bearer1Token());
    }

    @When("User 1 creates the assessment for facilitate")
    public void userCreatesTheAssessment() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("headOrganizer", FacilitateUserId);
        requestBody.put("assessmentProtocolId", 1);
        requestBody.put("groupId", FacilitateGroupId);
        String jsonBody = new Gson().toJson(requestBody);
        response = baseApi.postRequest("room-events/assessments", jsonBody, bearer1Token());
        ValidationUtils.validateJsonBody(response);
        // response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idValue = jsonPath.getString("roomEvent.id");
        System.out.println("======================idValue : " + idValue + "======================");
    }


    @Then("user gets the room event")
    public void userGetsTheRoomEvent() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue); // Assuming idValue is defined somewhere in the class
        response = baseApi.getRequest("room-events", bearer1Token(), requestBody, null, null, null, null);

    }


    @And("User gets the list of group users of the first group from the list of groups")
    public void userGetsTheListOfGroupUsersOfTheFirstGroupFromTheListOfGroups() {
        response = baseApi.getRequest("/groups/" + FirstGroupId + "/users", bearer1Token(), null, null, null, null, null);
        JsonPath jsonPath = response.jsonPath();
        List<String> userIds = jsonPath.getList("members.userId");
        FirstUserId = userIds.get(0);
    }

    @When("I send performance overview request with range {string} and metricId {string}")
    public void iSendPerformanceOverviewRequestWithRangeAndMetricId(String arg0, String arg1) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;
        switch (arg0) {
            case "7days":
                startDate = endDate.minusDays(7);
                break;
            case "30days":
                startDate = endDate.minusDays(30);
                break;
            case "12months":
                startDate = endDate.minusMonths(12);
                break;
            default:
                throw new IllegalArgumentException("Invalid range selection: " + arg0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue); // Assuming idValue is defined and set elsewhere in the class
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("mock", "false");
        requestBody.put("dimensionIds", "1,2");
        requestBody.put("granularity", "day");
        requestBody.put("uplift-app", "takeoff");
        requestBody.put("uplift-version", "2");
        requestBody.put("startDate", formattedStartDate);
        requestBody.put("endDate", formattedEndDate);
        requestBody.put("metricId", arg1);

        // Add groupId to the requestBody only if it's required
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId"); // Check if groupId should be included
        if (includeGroupId) {
            requestBody.put("groupId", FirstGroupId);
            requestBody.put("userId", FirstUserId);
        }

        response = baseApi.getRequest("performance/aggregate", bearer1Token(), requestBody, null, null, null, null);
    }

    @Then("I send delete request to delete the remove the user from the group")
    public void iSendDeleteRequestToDeleteTheRemoveTheUserFromTheGroup() {
        List<String> groupIds = TestContext.getGroupIds();
        for (String groupId : groupIds) {
            removeMember(groupId);
        }
    }
    private void removeMember(String groupId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("headOrganizer", FacilitateUserId);
        response = baseApi.deleteRequest("groups/" + groupId, null, bearer1Token());
    }

    @When("I send a request to performance overview request with range {string} and metricId {string}")
    public void iSendARequestToPerformanceOverviewRequestWithRangeAndMetricId(String arg0, String arg1) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;
        switch (arg0) {
            case "7days":
                startDate = endDate.minusDays(7);
                break;
            case "30days":
                startDate = endDate.minusDays(30);
                break;
            case "12months":
                startDate = endDate.minusMonths(12);
                break;
            default:
                throw new IllegalArgumentException("Invalid range selection: " + arg0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        Map<String, Object> requestBody = new HashMap<>();
        //requestBody.put("roomEventId", idValue); // Assuming idValue is defined and set elsewhere in the class
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("mock", "false");
        requestBody.put("dimensionIds", "1,2");
        requestBody.put("granularity", "day");
        requestBody.put("uplift-app", "takeoff");
        requestBody.put("uplift-version", "2");
        requestBody.put("startDate", formattedStartDate);
        requestBody.put("endDate", formattedEndDate);
        requestBody.put("metricId", arg1);

        // Add groupId to the requestBody only if it's required
        boolean includeGroupId = scenario.getSourceTagNames().contains("@WithGroupId"); // Check if groupId should be included
        if (includeGroupId) {
            requestBody.put("groupId", FirstGroupId);
            requestBody.put("userId", FirstUserId);
        }

        response = baseApi.getRequest("performance/raw", bearer1Token(), requestBody, null, null, null, null);
    }
}









