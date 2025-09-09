Feature: Search Flights on Ixigo

  Scenario: User searches for flights successfully
    Given user is on the Ixigo homepage
    When user selects departure city "<FromCity>"
    And user selects arrival city "<ToCity>"
    And user selects departure date "<DepDate>"
    And user selects return date "<RetDate>"
    And user clicks on the search button
    Then flight search results should be displayed

    Examples:
      | FromCity  | ToCity    | DepDate    | RetDate    |
      | Chennai   | Delhi     | 2025-09-15 | 2025-09-20 |
