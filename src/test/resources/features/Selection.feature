Feature: Flight Selection on Ixigo

  Scenario: User selects a flight from search results
    Given user is on the flight search results page
    When user selects a flight based on "<Criteria>"
    And user clicks on "Continue" or "Book Now"
    Then the flight should be added to the booking summary page

    Examples:
      | Criteria         |
      | Lowest Price     |
      | Non-stop Flights |
      | Preferred Airline|
