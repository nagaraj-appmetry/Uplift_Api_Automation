Feature: DELETE Requests

#  Scenario: Handle missing or invalid parameters for a DELETE request
#    When I send a DELETE request to delete group with invalid groupID
#    Then I should receive a response with status code 400
#
#  Scenario: Handle non-existent resource in a DELETE request
#    When I send a DELETE request to delete group with non-existent ID
#    Then I should receive a response with status code 404

  Scenario: Verify successful deletion in a DELETE request
    When I send a DELETE request to delete group with valid ID
    Then I should receive a response with status code 200

#  Scenario: DELETE Request to delete group with unauthorized user
#    When I send a DELETE request to delete group with valid ID without bearer token
#    Then I should receive a response with status code 401
#    And The response should contain error messages "Unauthorized"
#
#  Scenario: DELETE Request to delete group that has already been deleted
#    When I send a DELETE request to delete group with valid ID that has already been deleted
#    Then I should receive a response with status code 404
#    And The response should contain error messages "Group not found"
#
#  Scenario: DELETE Request to delete group with malformed ID
#    When I send a DELETE request to delete group with malformed ID
#    Then I should receive a response with status code 400
#    And The response should contain error messages "Invalid ID format"
#
#  Scenario: Delete a valid accepted group invite
#    When I send a DELETE request to delete an accepted group invite with valid inviteId
#    Then I should receive a response with status code 200
#
#  Scenario: Delete an accepted group invite with an invalid token
#    When I send a DELETE request to delete an accepted group invite with valid inviteId
#    Then I should receive a response with status code 401
#
#  Scenario: Delete a valid declined group invite
#    When I send a DELETE request to delete a declined group invite with valid inviteId
#    Then I should receive a response with status code 200
#
#  Scenario: Delete a declined group invite with an invalid token
#    When I send a DELETE request to delete a declined group invite with valid inviteId
#    Then I should receive a response with status code 401
