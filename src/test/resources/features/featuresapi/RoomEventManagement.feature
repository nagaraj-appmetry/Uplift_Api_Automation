Feature: Room Event Management

  Scenario: Create a Room Event

    When i send get request to get the my profile
    When I send a POST request to create Room Event template
    When User 1 fetches the Room Event ID of the newly created Template
#    When User 1 creates the assessment based on the Room Event template ID
#    Then user processes all txt files and creates assets
#    Then user processes all txt files and creates metrics
#    When I send a POST request to create aggregate metrics
#    When i send performance overview call to get insights data
#    When I send performance overview call with range "7days" and metricId "1"
#    Then I send a Get call to get the leaderboard value and verify with insights value
#    When I send performance overview call with range "30days" and metricId "1"
#    Then I send a Get call to get the leaderboard value and verify with insights value
#    When I send performance overview call with range "12months" and metricId "1"
#    Then I send a Get call to get the leaderboard value and verify with insights value
#    When I send performance overview call with range "7days" and metricId "2"
#    When I send performance overview call with range "30days" and metricId "2"
#    When I send performance overview call with range "12months" and metricId "2"


#  Scenario: Delete the old room assessment
#
#  When i send get call to get the list of past room events
#  Then i delete all the room events using post call
#  Then i verify if there are any room events present for it
#
#
#
#  Scenario: Get Data from insights and detailed insights and verify them
#
#    When i send performance overview call to get insights data
#    When i send performance overview call to get detailed insights data

