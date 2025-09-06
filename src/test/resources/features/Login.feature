Feature: Ixigo Login with Mobile Number

  # Positive Test
  Scenario Outline: Successful login with valid mobile number
    Given user is on the ixigo login page
    When user selects the mobile number login option
    And user enters mobile number "<Mobile>"
    And user clicks on the Continue button
    Then user should receive OTP and login should be successful
    And user should be redirected to the ixigo home page

    Examples:
      | Mobile Number |
      | 9345535247    |

  # Negative Test
  Scenario Outline: Unsuccessful login with invalid mobile number
    Given user is on the ixigo login page
    When user selects the mobile number login option
    And user enters mobile number "<Mobile>"
    And user clicks on the Continue button
    Then an error message "<ErrorMessage>" should be displayed

    Examples:
      | Mobile     | ErrorMessage                 |
      | 12345      | Please enter a valid number  |