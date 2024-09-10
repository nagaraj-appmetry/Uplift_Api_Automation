Feature: Group Management and User Invitation

  Scenario: Create a group, send an invite, accept the invite, and verify membership
    When I send a POST request to create group
    When User 1 fetches the group ID of the newly created group
    Then User 1 sends a group invite to User 2 with userId
    When User 2 accepts the group invite
    Then User 1 fetches the group details to verify the member count is 2
    And User 1 lists the group users to verify that User 2 is a member of the group