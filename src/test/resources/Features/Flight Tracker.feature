Feature: Flight Tracker Functionality

  Scenario Outline: Verify scheduled flights and view details from different airports
    Given user is on Ixigo homepage
    When user clicks on Flight Tracker
    And user navigates to Scheduled tab
    And user enters "<airport>" in Airport field
    And user clicks Search button
    Then flight results should be displayed
    When user clicks on first flight View Details
    Then flight detail page should be displayed

    Examples:
      | airport   |
      | Mumbai    |