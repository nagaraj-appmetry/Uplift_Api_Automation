package stepdefinitions.stepdefinitionsapi;

import com.google.gson.*;
import main.utilities.utilitiesapi.ConfigLoader;
import main.utilities.utilitiesapi.LoggerUtil;
import main.utilities.utilitiesapi.ValidationUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.testng.Assert;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Logger;

import pages.clientapi.APIClient;

public class APIStepDefinitions {

    private static String createdGroupId;
    private static String createdEventTemplateId;
    private static String inviteId;
    private static final String ABCD = "ABCDEF";
    private APIClient apiClient = new APIClient("https://nhimumobll.execute-api.us-west-2.amazonaws.com/development");
    private Response response;
    public String idValue;
    public String FilePath = "src/test/java/main/utilities/utilitiesapi/FileUtils/FailureClips";
    private double firstApiValue;
    private static String bearer1Token = ConfigLoader.getProperty("user1.token");
    private static String bearer2Token = ConfigLoader.getProperty("user2.token");
    private static String user2Id = ConfigLoader.getProperty("user2.ID");
    private static String Gname = ConfigLoader.getProperty("group.name");
    private static String Gdescription = ConfigLoader.getProperty("group.description");
    private static String Gpublic = ConfigLoader.getProperty("isPublic");
    private static String Gprofilepicture = ConfigLoader.getProperty("profilePicture");
    private static String Gcount = ConfigLoader.getProperty("membercount");
    private static final Logger logger = Logger.getLogger(APIStepDefinitions.class.getName());

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

/*.
.
.
.
.
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


    @When("I send a POST request to create group")
    public void createGroup() throws FileNotFoundException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", Gname);
        requestBody.put("description", Gdescription);
        requestBody.put("isPublic", Gpublic);
        requestBody.put("profilePicture", Gprofilepicture);
        String jsonBody = new Gson().toJson(requestBody);
//        System.out.println(apiClient.postRequest( "groups", jsonBody, bearerToken));
        response = apiClient.postRequest( "groups", jsonBody, bearer1Token);
        ValidationUtils.validateJsonBody(response);
       LoggerUtil.logResponse(response);
       // response.prettyPrint();
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

    @Then("User 1 sends a group invite to User 2 with userId")
    public void sendGroupInvite() {
        response = apiClient.sendGroupInvite(createdGroupId, user2Id, bearer1Token);
        ValidationUtils.validateJsonBody(response);
        inviteId = response.jsonPath().getString("inviteId");
        LoggerUtil.log("Invite sent with Invite ID: " + inviteId);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @When("User 2 accepts the group invite")
    public void acceptGroupInvite() {
        response = apiClient.acceptGroupInvite(createdGroupId, bearer2Token);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 fetches the group details to verify the member count is 2")
    public void verifyMemberCount() {
        response = apiClient.getGroupDetails(createdGroupId, bearer1Token);
        String memberCount = response.jsonPath().getString("memberCount");
        Assert.assertEquals(memberCount, Gcount, "Member count does not match.");
        LoggerUtil.log("Verified member count is: " + memberCount);
        ValidationUtils.validateJsonBody(response);
        LoggerUtil.logResponse(response);
    }

    @Then("User 1 lists the group users to verify that User 2 is a member of the group")
    public void listGroupUsers() {
        response = apiClient.listGroupUsers(createdGroupId, bearer1Token);
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
/*
.
.
.
 */

    @When("I send a POST request to create Room Event template")
    public void iSendAPOSTRequestToCreateRoomEventID() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("assessmentProtocolId", 1);
        String jsonBody = new Gson().toJson(requestBody);
        response = apiClient.postRequest( "room-event-templates", jsonBody, bearer1Token);
        //ValidationUtils.validateJsonBody(response);
       // response.prettyPrint();
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        String roomeventID = responseBody.getString("roomEventTemplateId");
        System.out.println(roomeventID);
    }

    @When("User 1 fetches the Room Event ID of the newly created Template")
    public void userFetchesTheRoomEventIDOfTheNewlyCreatedTemplate() {
        JSONObject responseBody = new JSONObject(response.getBody().asString());
        createdEventTemplateId = responseBody.getString("roomEventTemplateId");
        System.out.println("===============Template id : "+createdEventTemplateId+"==============================");
        //Assert.assertNotNull(createdGroupId, "Group ID should not be null.");
        LoggerUtil.log("Fetched Group ID: " + createdEventTemplateId);
        //ValidationUtils.validateJsonBody(response);
    }

    @When("User 1 creates the assessment based on the Room Event template ID")
    public void userCreatesTheAssessmentBasedOnTheRoomEventID() {
        response = apiClient.postRequest( "room-events/assessments/"+createdEventTemplateId, null, bearer1Token);
        //ValidationUtils.validateJsonBody(response);
        // response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idValue = jsonPath.getString("roomEvent.id");
        System.out.println("======================idValue : "+idValue+"======================");
    }

    @Then("user processes all txt files and creates assets")
    public void userProcessesAllTxtFilesAndCreatesAssets() throws IOException {
        String directoryPath = FilePath;  // Update with the actual path
        processTxtFilesAndCreateAssets(directoryPath);
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


    public void processTxtFilesAndCreateAssets(String directoryPath) throws IOException {
        // Load all .txt files in the directory
        File folder = new File(directoryPath);
        File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        // Properties object to store asset IDs
        Properties assetIdsProperties = new Properties();

        if (txtFiles != null) {
            for (File txtFile : txtFiles) {
                // Generate a unique asset ID
                String assetId = UUID.randomUUID().toString();
                logger.info("------------------------------------------------------");
                logger.info(assetId);
                logger.info("------------------------------------------------------");

                // Call the method to create assets
                createAsset(assetId);

                // Store the assetId in the properties object with the file name as the key
                assetIdsProperties.put(txtFile.getName(), assetId);
            }
        }

        // Save the asset IDs to a properties file
        try (FileOutputStream output = new FileOutputStream("src/test/java/main/utilities/utilitiesapi/FileUtils/InputFolder/assetIds.properties")) { // Update with the actual path
            assetIdsProperties.store(output, "Asset IDs");
        }
    }

    public void createAsset(String assetId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue); // Replace with the actual idValue

        // Properly set assetTag as a JSON array
        List<String> assetTags = Collections.singletonList("user_upload");
        requestBody.put("assetTag", assetTags);
        requestBody.put("assetId", assetId);

        // Convert the request body to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBody = gson.toJson(requestBody);
        // Assuming apiClient and bearer1Token are available and properly configured
        response = apiClient.postRequest("assets", jsonBody, bearer1Token);
    }

    @Then("user processes all txt files and creates metrics")
    public void userProcessesAllTxtFiles() throws IOException {
        // Path to your directory containing the .txt files
        String directoryPath = FilePath;

        // Load asset IDs from properties file
        Map<String, String> assetIds = loadAssetIdsFromProperties("src/test/java/main/utilities/utilitiesapi/FileUtils/InputFolder/assetIds.properties");

        // Initialize the request body map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue);
        requestBody.put("mock", false);

        // List to hold multiple capture objects
        List<Map<String, Object>> captures = new ArrayList<>();

        // Get a list of .txt files in the directory
        Files.list(Paths.get(directoryPath))
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(path -> {
                    try {
                        // Read the content of the file
                        String data = new String(Files.readAllBytes(path));

                        // Replace all occurrences of \\n followed by digits
                        data = data.replaceAll("\\\\n(?=\\d)", "\n");

                        // Replace all other occurrences of \\n (including at the end of the file)
                        data = data.replaceAll("\\\\n", "\n");
                        // Extract the dimension key and value from the file name
                        String fileName = path.getFileName().toString();
                        String[] fileNameParts = fileName.split("_", 3); // Split into 3 parts to handle the key and value correctly
                        String dimensionKey = fileNameParts[0] + "_" + fileNameParts[1]; // Combine the first two parts for the key
                        String dimensionValue = fileNameParts[2].replace(".txt", ""); // The third part is the value
                        if (dimensionValue.matches(".*-\\d+$")) {
                            dimensionValue = dimensionValue.replaceFirst("-\\d+$", "");
                        }

                        // Create dimension map
                        Map<String, String> dimension = new HashMap<>();
                        dimension.put(dimensionKey, dimensionValue);

                        // Generate a random asset ID
                        String assetId = assetIds.get(fileName);

                        // Create a capture map for each file
                        Map<String, Object> capture = new HashMap<>();
                        capture.put("assetId", assetId);
                        capture.put("dimension", Collections.singletonList(dimension));
                        capture.put("keypointModel", "Keypoints_3D-mobile_ios-general-pose_3d-videopose-coco_wholebody:v0");
                        capture.put("data", data);
                        capture.put("movement", "countermovement");
                        capture.put("activity", "jump");
                       // capture.put("framerate", 211.91908264160156);
                        capture.put("framerate", 240.2708740234375);

                        // Get the current time in seconds (Unix epoch time)
                        long epochTimestamp = System.currentTimeMillis() / 1000;
                        capture.put("capturedAt", epochTimestamp);

                        // Add the capture to the captures list
                        captures.add(capture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Add the captures list to the request body
        requestBody.put("captures", captures);

        // Convert the request body map to a JSON string
        String jsonBody = new Gson().toJson(requestBody);
        System.out.println("Generated JSON: " + jsonBody);  // Add this line for debugging

        // Write the captures list to a new JSON file
        try (FileWriter fileWriter = new FileWriter("src/test/resources/captures.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(captures, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send the request to the API endpoint
        response = apiClient.postRequest("/performance/metrics", jsonBody, bearer1Token);
        System.out.println(response);
    }


    @When("I send a POST request to create aggregate metrics")
    public void iSendAPOSTRequestToCreateAggregateMetrics() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue);
        requestBody.put("timezone","Asia/Kolkata");
        requestBody.put("mock",false);
        String jsonBody = new Gson().toJson(requestBody);
        response = apiClient.postRequest( "performance/aggregate", jsonBody, bearer1Token);
//        ValidationUtils.validateJsonBody(response);
//        // response.prettyPrint();
//        JSONObject responseBody = new JSONObject(response.getBody().asString());
//        String roomeventID = responseBody.getString("roomEventTemplateId");
//        System.out.println(roomeventID);
    }

    @When("i send performance overview call to get insights data")
    public void iSendPerformanceOverviewCall() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("timezone", "Asia/Kolkata");
        response = apiClient.getRequest( "performance/overview",queryParams, bearer1Token);
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
        requestBody.put("timezone","America/Los_Angeles");
        response=apiClient.getRequest("leaderboards",requestBody,bearer1Token);

        JSONObject responseBody = new JSONObject(response.getBody().asString());
        JSONArray leaderboard = responseBody.getJSONArray("leaderboard");
        JSONObject firstEntry = leaderboard.getJSONObject(0);
        double leaderboardValue = firstEntry.getDouble("value");

        // Compare the value from the first API with the second API
        if (this.firstApiValue == leaderboardValue) {
            System.out.println("Values match: " + this.firstApiValue + " == " + leaderboardValue);
        } else {
            System.out.println("Values do not match: " + this.firstApiValue + " != " + leaderboardValue);
        }
    }

    @When("I send performance overview call with range {string} and metricId {string}")
    public void iSendPerformanceOverviewCallToGetDetailedInsightsData(String rangeSelection, String metricIdSelection) {
        // Current date (end date)
        LocalDate endDate = LocalDate.now();

        // Calculate the start date based on user selection
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

        // Format the dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        // Prepare the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("roomEventId", idValue); // Assuming idValue is defined somewhere in the class
        requestBody.put("timezone", "Asia/Kolkata");
        requestBody.put("mock", "false");
        requestBody.put("dimensionIds", "1,2");
        requestBody.put("granularity", "day");
        requestBody.put("uplift-app", "takeoff");
        requestBody.put("uplift-version", "2");
        requestBody.put("startDate", formattedStartDate);
        requestBody.put("endDate", formattedEndDate);
        requestBody.put("metricId", metricIdSelection);

        // Print request body for debugging
        System.out.println("Request Body: " + requestBody);



        // Send the request to the API
        response = apiClient.getRequest("performance/aggregate", requestBody, bearer1Token);

    }

}





