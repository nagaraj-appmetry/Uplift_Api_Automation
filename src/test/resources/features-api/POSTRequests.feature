Feature: POST Requests

  @POST
  Scenario: Handle missing or invalid parameters for a POST request
    When I send a POST request to create group with name "", description "valid description", isPublic "false", profilePicture "valid_url"
    Then I should receive a response with status code 400
#    And The response should contain error messages "\"name\" is not allowed to be empty"
#    And The response should contain error message with type "JoiValidationError"

  @POST
  Scenario: Handle malformed data in a POST request
    When I send a POST request to create group with malformed data
    Then I should receive a response with status code 400
#    And The response should contain error messages "\"name\" must be a string"
#    And The response should contain error message with type "JoiValidationError"

  @POST
  Scenario: Handle long or complex data in a POST request
    When I send a POST request to create group with long name and description
    Then I should receive a response with status code 200

  @POST
  Scenario: Verify error message in a POST request
    When I send a POST request to create group with name "", description "", isPublic "", profilePicture ""
#    Then The response should contain error messages "\"name\" is not allowed to be empty"
#    And The response should contain error message with type "JoiValidationError"

  @POST1
  Scenario: POST Request to create group with already existing name
    When I send a POST request to create group with name "Nagaraj-APPMETRY", description "Test", isPublic "false", profilePicture ""
    Then I should receive a response with status code 200

  @POST
  Scenario: POST Request to create group with invalid JSON format
    When I send a POST request to create group with invalid JSON
    Then I should receive a response with status code 400
#    And The response should contain error messages "\"name\" is required"
#    And The response should contain error message with type "JoiValidationError"

  @POST
  Scenario: POST Request to create group with unauthorized user
    When I send a POST request to create group with name "NewGroup", description "Test", isPublic "true", profilePicture "" without bearer token
    Then I should receive a response with status code 401
    And The response should contain error messages "Unauthorized"

  @POST
  Scenario: POST Request to create group with unsupported media type
    When I send a POST request to create group with unsupported media type
    Then I should receive a response with status code 401
    And The response should contain error messages "Unauthorized"

  @POST
  Scenario: POST Request to create group with empty body
    When I send a POST request to create group with empty body
    Then I should receive a response with status code 400


  @POST
  Scenario: POST Request to create group with extremely large request body
    When I send a POST request to create group with extremely large request body
    Then I should receive a response with status code 400
#    And The response should contain error messages "\"name\" is required"
#    And The response should contain error message with type "JoiValidationError"
