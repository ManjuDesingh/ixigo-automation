Feature: Enter Passenger Details during Booking

  Scenario: User fills passenger information successfully
    Given the user launches the browser
    Then the Ixigo homepage should be displayed
    When the user searches for flights from "Chennai" to "Bangalore"
    And the user selects departure and return dates
    And the user clicks on search
    Then flight search results should be displayed
    When the user selects a flight from results
    And the user applies filters by price range "2000" to "5000" and airline "IndiGo"
    And the user clicks on proceed to booking
    Then the booking summary should be displayed
    When the user enters passenger details
      | FirstName | LastName | Gender | DateOfBirth |
      | John      | Doe      | Male   | 01-01-1990  |
    And the user selects seat preferences
      | SeatType |
      | Aisle    |
    And the user adds contact information
      | Email                | Phone       |
      | john.doe@example.com | 9876543210  |
    Then passenger details should be submitted successfully
