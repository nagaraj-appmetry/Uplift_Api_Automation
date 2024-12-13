Feature: Delete Groups

  @requiresLogin
  Scenario: Delete a list of groups
    When I send a GET request to groups with page 0, limit 500, role "owner"
    Then I send a DELETE request to delete all group