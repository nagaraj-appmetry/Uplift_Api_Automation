Feature: Concurrent Group Creation and Conflict Resolution Flow

  Scenario: Handle concurrent group creation with same name
    Given I attempt to create a group with a unique name
    When I simultaneously attempt to create another group with the same name
    Then The system should handle the conflict and return an error
    And Only one group with the name should exist
