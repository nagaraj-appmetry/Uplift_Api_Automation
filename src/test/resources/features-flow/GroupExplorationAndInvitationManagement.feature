Feature: Group Exploration and Invitation Management Flow

  Scenario: Explore available groups
    Given I explore available groups
    Then I should retrieve a list of groups

  Scenario: Check invitation status and decline invitation
    Given I check the invitation status for a group
    When The user declines the invitation
    Then The invitation status should reflect the decline
