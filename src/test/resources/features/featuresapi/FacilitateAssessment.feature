Feature: Room Event Management

  @requiresLogin
  @WithGroupId
  Scenario: Create a Room Event

    When User 1 creates the assessment for facilitate
    Then user gets the room event
    Then user processes all txt files and creates assets
    Then user processes all txt files and creates metrics
#    When I send a POST request to create aggregate metrics
    When I send performance overview call with range "7days" and metricId "1"
    When I send performance overview call with range "30days" and metricId "1"
    When I send performance overview call with range "12months" and metricId "1"
