Feature: Airport Cab Booking

  Scenario Outline: Positive cab booking flow from Home To Aiport
    Given user is on the search page
    When user clicks on Airport Cabs option
    And user selects Home To Aiport option
    And user enters pickup location as "<from>"
    And user enters drop location as "<to>"
    Then user clicks on cab search button
    And cab search results should be displayed

    Examples:
      | from    | to                       |
      | Chennai | Mumbai - CSMI Airport-T1 |