Feature: Flight Booking on Ixigo

  Scenario: User searches and selects a flight
    Given the user launches the browser
    And the user is on the Ixigo homepage
    When the user searches for flights from "Chennai" to "Bangalore"
    And the user selects departure and return dates
    And the user clicks on search
    Then flight search results should be displayed
    When the user selects a flight from results
    And the user applies filters by price range "2000" to "5000" and airline "IndiGo"
    And the user clicks on proceed to booking
    Then the booking summary should be displayed
    When the user enters passenger details
    And the user selects seat preferences
    And the user adds contact information
    Then passenger details should be submitted successfully
