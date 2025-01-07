Feature: View Profile Via Group's About page

  @requiresLogin
  Scenario: Go to Group's about page and view the profile as a different roles
  When I send a request to performance overview request with range "7days" and metricId "1"
#    When i send get request to get the my profile
#    When I send a GET request to groups with page 0, limit 500, role "member"
#    Then I send delete request to delete the remove the user from the group