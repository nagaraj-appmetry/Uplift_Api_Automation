Feature: Group Creation and User Invitation Flow

  Scenario: Create a new group and invite a user
    Given I send a POST request to create group with name "Nagaraj-APPMETRY", description "Test", isPublic "false", profilePicture ""
    When I send a GET request to "groups" with page 1, limit 50
    Then the new group should appear in the list

  Scenario: Send an invitation to a user
    Given I send a group invite to a user
    When I check pending invitations for the user
    Then the invitation should be pending

  Scenario: User accepts the invitation
    Given The user accepts the group invitation
    When I list group users
    Then The user should be added to the group
