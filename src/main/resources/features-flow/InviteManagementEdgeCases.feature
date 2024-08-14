Feature: Invite Management with Edge Cases Flow

  Scenario: Send multiple invitations to the same user
    Given I send multiple invitations to the same user
    When I check the invite status
    Then Only one active invitation should be tracked

  Scenario: Handle expired and invalid invitations
    Given An invitation is expired
    When I attempt to accept the expired invitation
    Then The system should prevent acceptance and return an error

  Scenario: Handle invalid invitation
    Given I attempt to accept an invalid invitation
    Then The system should handle the error gracefully
