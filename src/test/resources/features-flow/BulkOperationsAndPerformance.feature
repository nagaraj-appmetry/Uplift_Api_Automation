Feature: Bulk Operations and Performance Flow

  Scenario: Bulk create and manage groups and users
    Given I bulk create multiple groups
    When I bulk add users to a group
    And I bulk send invitations
    Then The system should handle the operations efficiently

  Scenario: Monitor system performance
    Given I monitor system performance during bulk operations
    Then The response times should be within acceptable limits
