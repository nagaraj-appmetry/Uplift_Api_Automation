Feature: Invalid Data and Error Handling Flow

  Scenario: Create and manage groups with invalid data
    Given I send a POST request to create group with name "", description "valid description", isPublic "false", profilePicture "valid_url"
    Then I should receive a response with status code 400

  Scenario: Send an invite with invalid data
    Given I attempt to send an invite with an invalid email or user ID
    Then The system should reject the invite with a clear error

  Scenario: Verify consistent error handling across APIs
    Given I use various endpoints with invalid inputs
    Then The system should provide consistent and clear error handling
