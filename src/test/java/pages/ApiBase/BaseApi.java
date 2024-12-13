package pages.ApiBase;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import main.utilities.utilitiesapi.LoggerUtil;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BaseApi {
    private RequestSpecification request;
    private static final Logger logger = LoggerFactory.getLogger(BaseApi.class);
   private final String baseUrl;
    public static String groupId = "e0a18cd1-9fa0-45fe-b679-bfcb837c1d6c";

    public BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    private RequestSpecification buildRequest(String token, String acceptType) {
        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 30000));
        RequestSpecification request = given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .header("Accept", acceptType);

        if (token != null) {
            request.header("Authorization", "Bearer " + token);
            request.header("uplift-app","takeoff");
            request.header("Cache-Control","no-store");
            request.header("uplift-version", "2");
            request.header("mock","false");
            request.header("version","1");
        }
        return request;
    }

    public Response getRequest(String endpoint, String token,
                               Map<String, Object> queryParams,
                               Map<String, String> headers,
                               Integer timeout,
                               Map<String, String> conditionalHeaders,
                               String pathVariable) {
        try {
            if (pathVariable != null) {
                endpoint = endpoint + "/" + pathVariable;
            }
            RequestSpecification request = buildRequest(token, "application/json");
            if (queryParams != null && !queryParams.isEmpty()) {
                request.queryParams(queryParams);
            }
            if (headers != null && !headers.isEmpty()) {
                headers.forEach(request::header);
            }
            if (conditionalHeaders != null && !conditionalHeaders.isEmpty()) {
                conditionalHeaders.forEach(request::header);
            }
            if (timeout != null) {
                RestAssured.config = RestAssured.config().httpClient(
                        HttpClientConfig.httpClientConfig().setParam("http.connection.timeout", timeout));
            }
            LoggerUtil.logRequest(request, endpoint, null);
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request to " + endpoint, e);
            throw e;
        }
    }


    public Response postRequest(String endpoint, String body, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            if (body != null && !body.isEmpty()) {
                request.body(body);
            }
            LoggerUtil.logRequest(request, endpoint, body);
            Response response = request.post(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }


    public Response sendGroupInvite(String groupId, String userId, String bearerToken) {
        String endpoint = "/groups/" + groupId + "/send-invite";
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            request.body(Map.of("userIds", new String[]{userId}));
            LoggerUtil.logRequest(request, endpoint, null);
            Response response = request.post(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }


    public Response putRequest(String endpoint, String body, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            request.body(body);
            Response response = request.put(endpoint);
            LoggerUtil.logRequest(request, endpoint, body);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during PUT request to " + endpoint, e);
            throw e;
        }
    }


    public Response deleteRequest(String endpoint, Map<String, Object> params, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            String paramsString = (params != null) ? params.toString() : "No parameters";
            LoggerUtil.logRequest(request, endpoint, paramsString);
            if (params != null && !params.isEmpty()) {
                request.queryParams(params);
            }
            Response response = request.delete(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during DELETE request to " + endpoint, e);
            throw e;
        }
    }


}
