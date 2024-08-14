Feature: Group Management and User Invitation

  Scenario: Create a group, send an invite, accept the invite, and verify membership
    When I send a POST request to create group with name "Test Group", description "Group for testing", isPublic "false", profilePicture ""
    When User 1 fetches the group ID of the newly created group
    Then User 1 sends a group invite to User 2 with userId "99fca8d5-c910-4d0f-99c7-c63a30233cf9"
    When User 2 accepts the group invite
    Then User 1 fetches the group details to verify the member count is "2"
    And User 1 lists the group users to verify that User 2 with userId "99fca8d5-c910-4d0f-99c7-c63a30233cf9" is a member of the group