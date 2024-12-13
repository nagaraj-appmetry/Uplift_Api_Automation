Feature: PUT Requests

  Scenario: Handle missing or invalid parameters for a PUT request
    When I send a PUT request to update group with name "", description "valid description", isPublic "true", profilePicture "valid_url"
    Then I should receive a response with status code 400

  Scenario: Handle malformed data in a PUT request
    When I send a PUT request to update group with malformed data
    Then I should receive a response with status code 400

  Scenario: Handle long or complex data in a PUT request
    When I send a PUT request to update group with long name and description
    Then I should receive a response with status code 200

  Scenario: Verify error message in a PUT request
    When I send a PUT request to update group with name "", description "", isPublic "", profilePicture ""
    Then The response should contain error messages "Invalid parameters"

  Scenario: PUT Request to update group with non-existent ID
    When I send a PUT request to update group with non-existent ID
    Then I should receive a response with status code 404
    And The response should contain error messages "Group not found"

  Scenario: PUT Request to update group with invalid JSON format
    When I send a PUT request to update group with invalid JSON
    Then I should receive a response with status code 400
    And The response should contain error messages "Invalid JSON format"

  Scenario: PUT Request to update group with unauthorized user
    When I send a PUT request to update group with name "UpdatedGroup", description "Updated", isPublic "true", profilePicture "" without bearer token
    Then I should receive a response with status code 401
    And The response should contain error message "Unauthorized"

  Scenario: PUT Request to update group with missing required fields
    When I send a PUT request to update group with missing required fields
    Then I should receive a response with status code 400
    And The response should contain error messages "Missing required fields"

  Scenario: PUT Request to update group with partial update
    When I send a PUT request to update group with partial update
    Then I should receive a response with status code 200
    And The response should contain the updated group details
