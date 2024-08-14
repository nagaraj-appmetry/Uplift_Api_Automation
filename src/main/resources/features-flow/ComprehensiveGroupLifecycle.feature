Feature: Comprehensive Group Lifecycle Flow

  Scenario: Full cycle from group creation to deletion
    Given I create a new group
    And I add multiple users to the group
    And I send invitations to external users
    When The users accept/decline invitations
    And I update group details and user roles
    And I remove certain users from the group
    Then I delete the group
    And The group should be deleted successfully
