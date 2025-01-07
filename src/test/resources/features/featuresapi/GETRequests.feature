Feature: GET Requests


#  Scenario: Validate status code for a valid GET request
#    When I send a GET request to groups
#    Then I should receive a response with status code 200
#
#  Scenario: Handle invalid token for a GET request
#    When I send a GET request to groups with invalid token
#    Then I should receive a response with status code 401
#
#  Scenario: Handle request with timeout
#    When I send a GET request to groups with a timeout of 1000 milliseconds
#    Then I should receive a response with status code 200

  Scenario: Verify pagination for GET request
    When I send a GET request to groups with page 0, limit 500
    Then I should receive a response with status code 200

#  Scenario: Verify response format
#    When I send a GET request to groups expecting "application/json" format
#    Then I should receive a response with status code 200
#
#  Scenario: Test caching with repeated GET requests
#    When I send repeated GET requests to groups to test caching
#    Then I should receive a response with status code 200
#
#  Scenario: Handle non-existent endpoint
#    When I send a GET request to a non-existent endpoint ours
#    Then I should receive a response with status code 403
#
#  Scenario: Verify sorting and filtering for GET request
#    When I send a GET request to groups with page 1, limit 10, sortBy "groupname"
#    Then I should receive a response with status code 200
#
#  Scenario: GET Request with invalid bearer token
#    When I send a GET request to groups with page 1, limit 10, invalid bearer token
#    Then I should receive a response with status code 401
#    And The response should contain error messages "Unauthorized"
#
#  Scenario: GET Request for groups with page number exceeding limit
#    When I send a GET request to groups with page 1000, limit 10
#    Then I should receive a response with status code 200
#
#  Scenario: GET Request for groups with negative page number
#    When I send a GET request to groups with page -1, limit 10
#    Then I should receive a response with status code 400
#    And The response should contain error messages "\"page\" must be larger than or equal to 0"
#
##  @GET
##  Scenario: GET Request with non-JSON response format
##    When I send a GET request to "groups" with response format "text/plain"
##    Then I should receive a response with status code 406
##    And The response should contain error message "Not Acceptable"
##
##  @GET
##  Scenario: GET Request with conditional header "If-Modified-Since" without modification
##    When I send a GET request to "groups" with "If-Modified-Since" header set to "Tue, 15 Nov 1994 08:12:31 GMT"
##    Then I should receive a response with status code 304
##    And The response should not contain body
##
##  @GET
##  Scenario: GET Request with conditional header "If-None-Match" with match
##    When I send a GET request to "groups" with "If-None-Match" header set to "xyzzy"
##    Then I should receive a response with status code 304
##    And The response should not contain body
##
##    @GET
##  Scenario: Handle conditional GET request
##    When I send a GET request to "groups" with If-Modified-Since "Mon, 1 Jan 2023 00:00:00 GMT" and If-None-Match "some_etag"
##    Then I should receive a response with status code 200
#
#
#  Scenario: Search for a user within a group with a valid query
#    When I send a GET request to search users within a group with valid groupId and query
#    Then I should receive a response with status code 200
#
#
#  Scenario: Search for a user within a group with an invalid token
#    When I send a GET request to search users within a group with valid groupId and query
#    Then I should receive a response with status code 401