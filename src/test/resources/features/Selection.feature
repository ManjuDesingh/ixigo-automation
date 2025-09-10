Feature: Applying Filters
Scenario Outline: Application of filters for selecting the flight
Given the user is on the selection page
And the user wants to apply the first filter recommended_filter as "<Recommended_filter>"
And the the second filter is selected Airlines as "<Airlines>"
And the user clicks third filter departure as "<Departure>"
And the user clicks fourth filter arrival as "<Arrival>"
Then select the first available flight
 

Examples:
|Recommended_filter|Airlines|Departure|Arrival|
|Non-stop|Air-India|Before 6 AM|12 PM - 6 PM|