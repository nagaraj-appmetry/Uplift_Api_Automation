Feature: Data Integrity and Consistency Flow

  Scenario: Ensure data integrity during simultaneous operations
    Given I create and update multiple groups simultaneously
    When I add and remove users from a group
    Then The group and user data should remain consistent

  Scenario: Delete a group during user management operations
    Given I delete a group while managing users
    Then The system should handle the deletion gracefully
