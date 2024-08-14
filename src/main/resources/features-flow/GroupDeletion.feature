Feature: Group Deletion Flow

  Scenario: Delete an existing group
    Given I delete an existing group
    When I attempt to retrieve the deleted group
    Then The group should not exist

  Scenario: Confirm deletion
    Given I list all groups
    Then The deleted group should be absent
