Feature: Logout from Ixigo

  Scenario: User logs out successfully
    Given user is logged in on the Ixigo homepage
    When user clicks on the profile icon
    And user clicks on the logout button
    Then user should be redirected to the Ixigo login page
    And a confirmation message "You have been logged out" should be displayed
