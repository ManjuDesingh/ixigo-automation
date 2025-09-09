Feature: Apply filters on flight search results

  Scenario: User applies price, airline, and stops filters
    Given User is on the search results page
    When User applies filter by price range "2000" to "5000"
    And User applies filter by airline "IndiGo"
    And User applies filter by stops "Non-stop"
    Then Filtered results should be displayed correctly
