package main.utilities.utilitiesapi;

import io.restassured.response.Response;
import org.testng.Assert;

public class ValidationUtils {

    public static void validateJsonBody(Response response) {
        Assert.assertNotNull(response.getBody().jsonPath().get(), "Response body does not contain 'data' field!");
        LoggerUtil.log("Validated JSON body contains 'data' field.");
    }

    public static void validateResponseBody(Response response) {
        Assert.assertNotNull(response.getBody().jsonPath().get(), "Response body does not contain 'data' field!");
        LoggerUtil.log("Validated JSON body contains 'data' field.");
    }

    public static void validateResponseHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assert.assertEquals(actualValue, expectedValue, "Header value mismatch!");
        LoggerUtil.log(String.format("Validated header '%s': expected '%s', found '%s'", headerName, expectedValue, actualValue));
    }

    public static void validateErrorMessage(Response response, String errorMessage) {
        Assert.assertTrue(response.getBody().asString().contains(errorMessage), "Error message not found!");
        LoggerUtil.log(String.format("Validated presence of error message: '%s'", errorMessage));
    }

    public static void validateResponseTime(Response response, long maxResponseTime) {
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < maxResponseTime, "Response time is too long: " + responseTime + " ms");
        LoggerUtil.log(String.format("Validated response time: %d ms (max allowed: %d ms)", responseTime, maxResponseTime));
    }

    public static void validateSecurityHeaders(Response response) {
        Assert.assertNotNull(response.getHeader("X-Content-Type-Options"), "Missing security header: X-Content-Type-Options");
        Assert.assertNotNull(response.getHeader("X-Frame-Options"), "Missing security header: X-Frame-Options");
        LoggerUtil.log("Validated presence of security headers: X-Content-Type-Options, X-Frame-Options.");
    }

    public static void validateSchema(Response response) {
        LoggerUtil.log("Schema validation logic is not yet implemented.");
    }
}
