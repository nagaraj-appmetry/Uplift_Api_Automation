package com.example.clients;

import com.example.utilities.LoggerUtil;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIClient {
    public static final String BEARER_TOKEN = "eyJraWQiOiJQVkQwUDhjcHhIb2w2YitRNEt0VEFLR0hZTDdpKzlQcXB3SjBWZ2N5eDVJPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJiNjZiZmRkYy1kNWNlLTRjNTktOGE4YS1mYWU4ZDRkMzlkOGQiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMi5hbWF6b25hd3MuY29tXC91cy13ZXN0LTJfM2RMTHJXTXh0IiwiY29nbml0bzp1c2VybmFtZSI6ImI2NmJmZGRjLWQ1Y2UtNGM1OS04YThhLWZhZThkNGQzOWQ4ZCIsImF1ZCI6IjNxN25qY2hmN2VwbmI0ZzNyM3ZndWRwbThsIiwiZXZlbnRfaWQiOiJhMDg2MzM2YS03YmIzLTQ3NzEtOTlkZC02OWE2MTVmN2UxZGYiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcyMzYxNzU4NCwibmFtZSI6Im5hZ2FyYWogbSIsImV4cCI6MTcyMzcwNDAyNiwiaWF0IjoxNzIzNjE3NjI2LCJlbWFpbCI6Im5hZ2FyYWpAYXBwbWV0cnkuY29tIn0.sNFiOWIyCuQlk0qcfqQSN6HXofQspDFkihRx81-A7riUx7i7DN8xNqwxQz-ai2GaCRgjxPu12p8mJ8cZu9ZfMfYxSN0gqgEB37FEztK48lNzTQRawBn0aGx7yP9q_3pa49OwOOS_WjSkTeR785emDytflcERRul0_wclK2XowsqB6rxikAEhi5KgzpnBS8l-yEmA0j8AaBjLfPoeRt4X7Xq-54_t-9vbkoF3DbIjtwpo8ds7pLNWue8uajjKrmNgamjMdGfJSDHpDpionWLhLuAk9O_A3OZ2pxHfJdlPn5Qx6YjtUy1IZU-zdaHJ0At8q6R6NxreePJnFhKOC_NsHA";
    public static final String INVALID_TOKEN = "cXB3SjBWZ2N5eDVJPSIsIm";
    public static final String EMPTY_TOKEN = " ";
    private RequestSpecification request;
    private static final Logger logger = LoggerFactory.getLogger(APIClient.class);
//    private final String baseUrl;
    public static String GROUP_ID = "e0a18cd1-9fa0-45fe-b679-bfcb837c1d6c";
    private int groupId;

//    public APIClient() {
//        this.baseUrl = baseUrl;
//        RestAssured.baseURI = baseUrl;
//    }

    public Response getRequest(String endpoint, Map<String, Object> params, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            if (params != null) {
                request.params(params);
            }
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
            request.body(body);
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
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during PUT request to " + endpoint, e);
            throw e;
        }
    }


    public Response deleteRequest(String endpoint, String params, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            Response response = request.delete(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during DELETE request to " + endpoint, e);
            throw e;
        }
    }

    public Response getRequestWithTimeout(String endpoint, Map<String, Object> params, String token, int timeout) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            if (params != null) {
                request.params(params);
            }
            RestAssured.config = RestAssured.config()
                    .httpClient(RestAssured.config().getHttpClientConfig().setParam("http.connection.timeout", timeout));

            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request to " + endpoint, e);
            throw e;
        }
    }

    public Response getRequestWithFormat(String endpoint, Map<String, Object> params, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
            if (params != null) {
                request.params(params);
            }
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request to " + endpoint, e);
            throw e;
        }
    }

    public Response getRequestWithHeader(String endpoint, String headerName, String headerValue, String bearerToken) {
        try {
            RequestSpecification request = buildRequest(null, "application/json");
            request.header(headerName, headerValue);
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request with header to " + endpoint, e);
            throw e;
        }
    }

    public Response getRequestWithCondition(String endpoint, String bearerToken, String ifModifiedSince, String ifNoneMatch, String noneMatch) {
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            if (ifModifiedSince != null) {
                request.header("If-Modified-Since", ifModifiedSince);
            }
            if (ifNoneMatch != null) {
                request.header("If-None-Match", ifNoneMatch);
            }
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request with condition to {}", endpoint, e);
            throw e;
        }
    }

    public Response getGroupsWithSortingAndFiltering(String endpoint,String bearerToken, int page, int limit, String sortBy) {
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            request.queryParam("page", page)
                    .queryParam("limit", limit)
                    .queryParam("sortBy", sortBy);
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request with sorting and filtering to /groups", e);
            throw e;
        }
    }

    public Response getGroups(String endpoint, String bearerToken, int page, int limit) {
        try {
            RequestSpecification request = buildRequest(BEARER_TOKEN, "application/json");
            request.queryParam("page", page)
                    .queryParam("limit", limit);
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request for paginated groups to /groups", e);
            throw e;
        }
    }

    public Response postRequestWithUnsupportedMediaType(String endpoint, String requestBody, String bearerToken) {
        try {
            RequestSpecification request = buildRequest(null, "application/unsupported");
            request.body(requestBody);
            Response response = request.post(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request with unsupported media type to " + endpoint, e);
            throw e;
        }
    }

//    private RequestSpecification buildRequest(String token, String acceptType) {
//        RequestSpecification request = given()
//                .filter(new RequestLoggingFilter())
//                .filter(new ResponseLoggingFilter())
//                .header("Accept", acceptType);
//        if (token != null) {
//            System.out.println("Using token: " + token);
//            request.header("Authorization", "Bearer " + token);
//        }
//        return request;
//    }


    /*
    public Response getGroups(String endpoint, String bearerToken, int page, int limit) {
        try {
            RequestSpecification request = buildRequest(BEARER_TOKEN, "application/json");
            request.queryParam("page", page)
                    .queryParam("limit", limit);
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request for paginated groups to /groups", e);
            throw e;
        }
    }
     */
    public Response deleteAcceptedGroupInvite(String endpoint, String token) {
        try {
            RequestSpecification request = buildRequest(BEARER_TOKEN, "application/json");

            request.queryParam("groupId", GROUP_ID);
            Response response = request.delete(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request for paginated groups to /groups", e);
            throw e;
        }
    }

    public Response deleteDeclinedGroupInvite(String endpoint, String token) {
        try {
            RequestSpecification request = buildRequest(BEARER_TOKEN, "application/json");

            request.queryParam("groupId", GROUP_ID);
            Response response = request.delete(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during GET request for paginated groups to /groups", e);
            throw e;
        }
    }


    /*


    .....

    ....

     */

    private RequestSpecification buildRequest(String token, String acceptType) {
        RequestSpecification request = given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .header("Accept", acceptType);
        if (token != null) {
            System.out.println("Using token: " + token);
            request.header("Authorization", "Bearer " + token);
        }
        return request;
    }

    public static final String BASE_URL = "https://nhimumobll.execute-api.us-west-2.amazonaws.com/development";

    public Response createGroup(Map<String, String> groupDetails, String bearerToken) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType("application/json")
                .body(groupDetails) // Passing the map directly as the request body
                .when()
                .post(BASE_URL + "/groups");
    }


    public Response sendGroupInvite(String groupId, String userId, String bearerToken) {
        String endpoint = BASE_URL + "/groups/" + groupId + "/send-invite";
        return RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType("application/json")
                .body(Map.of("userIds", new String[]{userId}))
                .when()
                .post(endpoint);
    }

    public Response acceptGroupInvite(String groupId, String bearerToken) {
        String endpoint = BASE_URL + "/groups/" + groupId + "/accept-invite";
        return RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .delete(endpoint);
    }

    public Response getGroupDetails(String groupId, String bearerToken) {
        String endpoint = BASE_URL + "/groups/" + groupId;
        return RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get(endpoint);
    }

    public Response listGroupUsers(String groupId, String bearerToken) {
        String endpoint = BASE_URL + "/groups/" + groupId + "/users";
        return RestAssured.given()
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .get(endpoint);
    }

}
