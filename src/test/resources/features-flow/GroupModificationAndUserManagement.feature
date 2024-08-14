Feature: Group Modification and User Management Flow

  Scenario: Update group information
    Given I update the group with new details
    When I search for a specific user within the group
    Then The user should be found in the group

  Scenario: Update user's role in the group
    Given I update the user's role in the group
    When I remove the user from the group
    Then The user should be removed from the group
