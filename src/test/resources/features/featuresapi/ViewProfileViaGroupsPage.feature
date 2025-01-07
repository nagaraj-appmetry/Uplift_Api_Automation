Feature: View Profile Via Group's About page

  @requiresLogin
  @WithGroupId
  Scenario: Go to Group's about page and view the profile as a different roles

    When I send a GET request to groups with page 0, limit 500
    Then I send a GET request to groups with page 0, limit 500, role "owner"
    And User gets the list of group users of the first group from the list of groups
    When I send performance overview request with range "7days" and metricId "1"
    Then I send a GET request to groups with page 0, limit 500, role "admin"
    And User gets the list of group users of the first group from the list of groups
    When I send performance overview request with range "7days" and metricId "1"
    When I send a GET request to groups with page 0, limit 500, role "member"
    And User gets the list of group users of the first group from the list of groups
    When I send performance overview request with range "7days" and metricId "1"
