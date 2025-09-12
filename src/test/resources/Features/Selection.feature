Feature: Applying Filters

  Scenario Outline: Application of filters for selecting the flight
    Given the user is on the selection page
    And the user wants to apply the first filter recommended_filter as "<filter_type>"
    And the the second filter is selected Airlines as "<Airlines>"
    And the user clicks third filter departure as "<Departure>"
    And the user clicks fourth filter arrival as "<Arrival>"
    Then select the first available flight

    Examples:
      | filter_type | Airlines | Departure                         | Arrival                     |
      | Non-Stop    | IndiGo   | Departure from Chennai 06AM-12PM  | Arrival at Mumbai 06AM-12PM |