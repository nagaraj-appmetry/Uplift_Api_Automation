Feature: Group Privacy and Access Control Flow

  Scenario: explore public groups
    When I explore groups as a different user
    Then The public group should be visible

  Scenario: Attempt to access a private group without an invite
    Given I create a private group
    When I explore groups as a different user
    Then The private group should not be visible
