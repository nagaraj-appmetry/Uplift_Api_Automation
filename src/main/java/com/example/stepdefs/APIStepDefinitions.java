package com.example.stepdefs;

import com.example.utilities.ConfigLoader;
import com.example.utilities.LoggerUtil;
import com.example.utilities.ValidationUtils;
import com.google.gson.Gson;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.clients.APIClient;

public class APIStepDefinitions {

    private static String createdGroupId;
    private static String inviteId;
    private static final String ABCD = "ABCDEF";
    private APIClient apiClient = new APIClient("https://nhimumobll.execute-api.us-west-2.amazonaws.com/development");
    private Response response;

    @Then("I should receive a response with status code {int}")
    public void validateStatusCode(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
        ValidationUtils.validateJsonBody(response);
        ValidationUtils.validateResponseTime(response, 2000);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_GET_request_to(String endpoint) {
        response=apiClient.getRequest(endpoint,null, ConfigLoader.getProperty("user1.token"));
    }

    @Then("The response should not contain body")
    public void the_response_should_not_contain_body() {
        Assert.assertEquals(response.getBody().asString().isEmpty(),"The response body is not empty");
    }

    @When("I send a GET request to {string} with invalid token")
    public void sendGetRequestWithInvalidToken(String endpoint) {
        response = apiClient.getRequest(endpoint, null, ConfigLoader.getProperty("invalid.token"));
    }

    @When("I send a GET request to {string} with a timeout of {int} milliseconds")
    public void sendGetRequestWithTimeout(String endpoint, int timeout) {
        response = apiClient.getRequestWithTimeout(endpoint, null, ConfigLoader.getProperty("user1.token"), timeout);
    }

    @When("I send a GET request to {string} with page {int}, limit {int}")
    public void sendGetRequestWithPagination(String endpoint, int page, int limit) {
        response = apiClient.getRequest(endpoint,null, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a GET request to {string} expecting {string} format")
    public void sendGetRequestWithExpectedFormat(String endpoint, String format) {
        response = apiClient.getRequestWithFormat(endpoint, null, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send repeated GET requests to {string} to test caching")
    public void sendRepeatedGetRequestsToTestCaching(String endpoint) {
        for (int i = 0; i < 5; i++) {
            response = apiClient.getRequest(endpoint, null, ConfigLoader.getProperty("user1.token"));
        }
    }

    @When("I send a GET request to {string} with Accept header {string}")
    public void sendGetRequestWithAcceptHeader(String endpoint, String acceptHeader) {
        response = apiClient.getRequestWithFormat(endpoint, null, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a GET request to a non-existent endpoint {string}")
    public void sendGetRequestToNonExistentEndpoint(String endpoint) {
        response = apiClient.getRequest(endpoint, null, ConfigLoader.getProperty("user1.token"));
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @When("I send a GET request to {string} with If-Modified-Since {string} and If-None-Match {string}")
    public void sendGetRequestWithConditions(String endpoint, String ifModifiedSince, String ifNoneMatch) {
        response = apiClient.getRequestWithCondition(endpoint, null, ConfigLoader.getProperty("user1.token"), ifModifiedSince, ifNoneMatch);
    }

    @When("I send a GET request to {string} with page {int}, limit {int}, sortBy {string}")
    public void sendGetRequestWithSortingAndFiltering(String endpoint, int page, int limit, String sortBy) {
        response = apiClient.getGroupsWithSortingAndFiltering(endpoint,ConfigLoader.getProperty("user1.token"), page, limit, sortBy);
    }

    @When("I send a GET request to {string} with page {int}, limit {int}, invalid bearer token")
    public void sendGetRequestWithInvalidBearerToken(String endpoint,int page, int limit ) {
        response = apiClient.getRequest(endpoint, null, ConfigLoader.getProperty("invalid.token"));
    }

    @When("I send a GET request for {string} with page number {int} and limit {int}")
    public void sendGetRequestForGroupsWithPageNumberAndLimit(String endpoint, int page, int limit) {
        response = apiClient.getGroups(endpoint, ConfigLoader.getProperty("user1.token"), page, limit);
    }

    @When("I send a GET request to {string} with response format {string}")
    public void sendGetRequestWithResponseFormat(String endpoint, String format) {
        response = apiClient.getRequestWithFormat(endpoint, null, format);
    }

    @When("I send a GET request to {string} with {string} header set to {string}")
    public void sendGetRequestWithConditionalHeader(String endpoint, String headerName, String headerValue) {
        response = apiClient.getRequestWithHeader(endpoint, headerName, headerValue, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a POST request to create group with name {string}, description {string}, isPublic {string}, profilePicture {string} without bearer token")
    public void sendPostRequestToCreateGroupWithoutBearerToken(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", false); // Convert isPublic to a boolean
        requestBody.put("profilePicture", profilePicture);
        String jsonBody = new Gson().toJson(requestBody);
        response = apiClient.postRequest("groups", jsonBody, ConfigLoader.getProperty("empty.token"));
    }

    @When("I send a POST request to create group with malformed data")
    public void sendPostRequestWithMalformedData() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", 123);
        requestBody.put("description", true);
        requestBody.put("isPublic", false);
        requestBody.put("profilePicture", 456);
        String jsonBody = new Gson().toJson(requestBody);
        response = apiClient.postRequest("groups", jsonBody, ConfigLoader.getProperty("user1.token"));
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
        response = apiClient.postRequest("groups", jsonBody, ConfigLoader.getProperty("user1.token"));
    }


    @When("I send a POST request to create group with invalid JSON")
    public void sendPostRequestWithInvalidJson() {
        String invalidJson = "{\"name\": \"NewGroup\", \"description\": \"Test\", \"isPublic\": \"true\", \"profilePicture\": \"}";
        response = apiClient.postRequest("groups", invalidJson, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a POST request to create group with unsupported media type")
    public void sendPostRequestWithUnsupportedMediaType() {
        String requestBody = "{\"name\": \"NewGroup\", \"description\": \"Test\", \"isPublic\": \"true\", \"profilePicture\": \"\"}";
        response = apiClient.postRequestWithUnsupportedMediaType("groups", requestBody, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a POST request to create group with empty body")
    public void sendPostRequestWithEmptyBody() {
        response = apiClient.postRequest("groups", "", ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a POST request to create group with extremely large request body")
    public void sendPostRequestWithExtremelyLargeRequestBody() {
        StringBuilder largeRequestBody = new StringBuilder("{\"name\": \"NewGroup\", \"description\": \"");
        for (int i = 0; i < 10; i++) {
            largeRequestBody.append("large_body_data");
        }
        largeRequestBody.append("\", \"isPublic\": \"true\", \"profilePicture\": \"\"}");
        String jsonBody = new Gson().toJson(largeRequestBody);
        response = apiClient.postRequest("groups", jsonBody, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to update group with name {string}, description {string}, isPublic {string}, profilePicture {string}")
    public void sendPutRequestToUpdateGroup(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", isPublic);
        requestBody.put("profilePicture", profilePicture);
        response = apiClient.putRequest("groups/1", requestBody.toString(), ConfigLoader.getProperty("user1.token"));
    }


    @When("I send a PUT request to update group with malformed data")
    public void sendPutRequestWithMalformedData() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", 123);
        requestBody.put("description", true);
        requestBody.put("isPublic", "not_a_boolean");
        requestBody.put("profilePicture", 456);
        response = apiClient.putRequest("groups/1", requestBody.toString(), ConfigLoader.getProperty("user1.token"));
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
        response = apiClient.putRequest("groups/1", requestBody.toString(), ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to update group with non-existent ID")
    public void sendPutRequestWithNonExistentId() {
        String requestBody = "{\"name\": \"UpdatedGroup\", \"description\": \"Updated\", \"isPublic\": \"true\", \"profilePicture\": \"\"}";
        response = apiClient.putRequest("groups/9999", requestBody, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to update group with invalid JSON")
    public void sendPutRequestWithInvalidJson() {
        String invalidJson = "{\"name\": \"UpdatedGroup\", \"description\": \"Updated\", \"isPublic\": \"true\", \"profilePicture\": \"";
        response = apiClient.putRequest("groups/1", invalidJson, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to update group with partial update")
    public void sendPutRequestWithPartialUpdate() {
        String requestBody = "{\"description\": \"Updated\"}";
        response = apiClient.putRequest("groups/1", requestBody, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to update group with missing required fields")
    public void sendPutRequestWithMissingRequiredFields() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "");
        requestBody.put("description", "");
        response = apiClient.putRequest("groups/update", requestBody.toString(), ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete group with invalid groupID")
    public void sendDeleteRequestWithInvalidID() {
        response = apiClient.deleteRequest("groups", ABCD, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete group with non-existent ID")
    public void sendDeleteRequestWithNonExistentID() {
        response = apiClient.deleteRequest("groups", ABCD, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete group with valid ID")
    public void sendDeleteRequestWithValidID() {
        response = apiClient.deleteRequest("groups", APIClient.GROUP_ID, ConfigLoader.getProperty("user1.token")); // Assuming ID 1 for demo
    }

    @When("I send a DELETE request to delete group with valid ID without bearer token")
    public void sendDeleteRequestWithValidIdWithoutBearerToken() {
        response = apiClient.deleteRequest("groups", null, ConfigLoader.getProperty("invalid.token"));
    }

    @When("I send a DELETE request to delete group with valid ID that has already been deleted")
    public void sendDeleteRequestWithValidIdThatHasAlreadyBeenDeleted() {
        response = apiClient.deleteRequest("groups", APIClient.GROUP_ID, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete group with malformed ID")
    public void sendDeleteRequestWithMalformedId() {
        response = apiClient.deleteRequest("groups",ABCD, ConfigLoader.getProperty("user1.token"));
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

/*............................................................
...............................
................................
...............................
 */

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
        response = apiClient.postRequest(endpoint, body, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a PUT request to {string} with body {string}")
    public void i_send_a_PUT_request_to_with_body(String endpoint, String body) {
        response = apiClient.putRequest(endpoint, body, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete group invite with ID {string}")
    public void i_send_a_DELETE_request_to_delete_group_invite_with_id(String inviteId) {
        response = apiClient.deleteAcceptedGroupInvite(inviteId, ConfigLoader.getProperty("user1.token"));
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
    public void sendGetRequestToSearchUsersWithinGroup(String groupId, String query, String token) {
        String endpoint = String.format("groups/%s/users?search=%s", groupId, query);
        response = apiClient.getRequest(endpoint, null, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete an accepted group invite with valid inviteId")
    public void sendDeleteRequestToDeleteAcceptedGroupInvite(String endpoint, String token) {
        response = apiClient.deleteAcceptedGroupInvite(endpoint, ConfigLoader.getProperty("user1.token"));
    }

    @When("I send a DELETE request to delete a declined group invite with valid inviteId")
    public void sendDeleteRequestToDeleteDeclinedGroupInvite(String endpoint, String token) {
        response = apiClient.deleteDeclinedGroupInvite(endpoint, ConfigLoader.getProperty("user1.token"));
    }

    /*
    .
    .
    .
    .
    .
    .
     */


    @When("I send a POST request to create group with name {string}, description {string}, isPublic {string}, profilePicture {string}")
    public void createGroup(String name, String description, String isPublic, String profilePicture) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("isPublic", isPublic);
        requestBody.put("profilePicture", profilePicture);
        String jsonBody = new Gson().toJson(requestBody);
        String bearerToken = ConfigLoader.getProperty("user1.token");
        response = apiClient.postRequest( "groups", jsonBody, bearerToken);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
        response.prettyPrint();
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        String groupId = responseBody.getString("groupId");

        //assertThat("Group ID should not be null.", groupId, notNullValue());
    }

    @When("User 1 fetches the group ID of the newly created group")
    public void fetchGroupId() {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        createdGroupId = responseBody.getString("groupId");
        System.out.println("=========================="+createdGroupId+"==============================");
        //Assert.assertNotNull(createdGroupId, "Group ID should not be null.");
        LoggerUtil.log("Fetched Group ID: " + createdGroupId);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 sends a group invite to User 2 with userId {string}")
    public void sendGroupInvite(String userId) {
        String bearerToken = ConfigLoader.getProperty("user1.token");
        response = apiClient.sendGroupInvite(createdGroupId, userId, bearerToken);
        ValidationUtils.validateJsonBody(response);
        inviteId = response.jsonPath().getString("inviteId");
        LoggerUtil.log("Invite sent with Invite ID: " + inviteId);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @When("User 2 accepts the group invite")
    public void acceptGroupInvite() {
        String bearerToken = ConfigLoader.getProperty("user2.token");
        response = apiClient.acceptGroupInvite(createdGroupId, bearerToken);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 fetches the group details to verify the member count is {string}")
    public void verifyMemberCount(String expectedCount) {
        String bearerToken = ConfigLoader.getProperty("user1.token");
        response = apiClient.getGroupDetails(createdGroupId, bearerToken);
        String memberCount = response.jsonPath().getString("memberCount");
        Assert.assertEquals(memberCount, expectedCount, "Member count does not match.");
        LoggerUtil.log("Verified member count is: " + memberCount);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 lists the group users to verify that User 2 with userId {string} is a member of the group")
    public void listGroupUsers(String userId) {
        String bearerToken = ConfigLoader.getProperty("user1.token");
        response = apiClient.listGroupUsers(createdGroupId, bearerToken);
        JsonPath jsonPath = response.jsonPath();
        List<String> userIds = jsonPath.getList("members.userId");
        if (userIds == null) {
            LoggerUtil.log("The 'members' field is missing or null. The response might be incorrect.");
            Assert.fail("The 'members' field is missing or null. The response might be incorrect.");
        } else {
            boolean isUserPresent = userIds.contains(userId);
            Assert.assertTrue(isUserPresent, "User 2 is not a member of the group.");
            LoggerUtil.log("Verified that User 2 with userId " + userId + " is a member of the group.");
        }
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }


}

