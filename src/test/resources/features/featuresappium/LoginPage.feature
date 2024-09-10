Feature: Login to Uplift Application
  Scenario: Successful Login
    Given the user is in the homepage and clicks on login
    When the user enters "nagaraj@appmetry.com" as email and "Nagaraj123" as password
    And user clicks on the Login button
    And he lands on the insights page
    And user clicks on the Assessment button at the menu bar
    And user clicks on the "Jump Higher Assessment"
    Then user should see the following movements present
      | movementname | reps |
      | Countermovement Jump - Arm Swing | 3 reps |
      | Countermovement Jump - Hands on Hips | 3 reps |
    And user clicks on Start Assessment button
    And user lands on "Countermovement Jump - Arm Swing" page which would be the "First Movement"
    And user clicks on the Arrow
    And user should find the progression title "Jump Higher Assessment", movement title "Countermovement Jump - Arm Swing" and videos as "3  remaining"
    And user records a video
    And the text should change to "2  remaining"
    And user records a video
    And the text should change to "1  remaining"
    And user deletes a video
    And the text should change to "2  remaining"
    And user records a video
    And the text should change to "1  remaining"
    And user records a video post which the user should see movement completed
    And user moves on to next
    And user lands on "Countermovement Jump - Hands on Hips" page which would be the "Next Movement!"
    And user clicks on the Arrow
    And user records a video
    And the text should change to "2  remaining"
    And user records a video
    And the text should change to "1  remaining"
    And user deletes a video
    And the text should change to "2  remaining"
    And user records a video
    And the text should change to "1  remaining"
    And user records a video post which the user should see movement completed
    And user moves on to next
    And user gets notified that progression is finished and can see his results.
    And user clicks on view my results button
    Then he lands on the insights page

#  Scenario: Successful Login
#    Given the user is in the homepage and clicks on login
#    When the user enters "vik@appmetry.com" as email and "1234567890" as password
#    And user clicks on the Login button
#    And he lands on the insights page
#    Then driver is quit