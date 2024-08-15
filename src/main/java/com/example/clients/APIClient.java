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
    private RequestSpecification request;
    private static final Logger logger = LoggerFactory.getLogger(APIClient.class);
   private final String baseUrl;
    public static String GROUP_ID = "e0a18cd1-9fa0-45fe-b679-bfcb837c1d6c";
    private int groupId;


    public APIClient(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }


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

    public Response getGroups(String endpoint, String token, int page, int limit) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");
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

    //=======================================
    public Response deleteAcceptedGroupInvite(String endpoint, String token) {
        try {
            RequestSpecification request = buildRequest(token, "application/json");

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
            RequestSpecification request = buildRequest(token, "application/json");

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

    public static final String BASE_URL = "https://nhimumobll.execute-api.us-west-2.amazonaws.com/development";

    public <json> Response postRequesttocreategroup(String endpoint, json body, String token) {
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

    public Response sendGroupInvite(String groupId, String userId, String bearerToken) {
        String endpoint = "/groups/" + groupId + "/send-invite";
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            request.body(Map.of("userIds", new String[]{userId}));
            Response response = request.post(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }

    public Response acceptGroupInvite(String groupId, String bearerToken) {
        String endpoint = "/groups/" + groupId + "/accept-invite";
                try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            Response response = request.delete(endpoint);
                    LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }

    public Response getGroupDetails(String groupId, String bearerToken) {
        String endpoint = "/groups/" + groupId;
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }

    public Response listGroupUsers(String groupId, String bearerToken) {
        String endpoint = "/groups/" + groupId + "/users";
        try {
            RequestSpecification request = buildRequest(bearerToken, "application/json");
            Response response = request.get(endpoint);
            LoggerUtil.logResponse(response);
            return response;
        } catch (Exception e) {
            logger.error("Error during POST request to " + endpoint, e);
            throw e;
        }
    }


}
