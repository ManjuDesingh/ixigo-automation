package com.stepdefinitions;

import com.pages.FlightTrackerPage;
import com.stepdefinitions.Hooks;
import com.stepdefinitions.Hooks;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class FlightTrackerSteps {

    FlightTrackerPage tracker;

    public FlightTrackerSteps() {
        this.tracker = new FlightTrackerPage(Hooks.driver, Hooks.extTest);
    }

    @Given("user is on Ixigo homepage")
    public void user_is_on_ixigo_homepage() {
        Hooks.driver.get("https://www.ixigo.com/");
    }

    @When("user clicks on Flight Tracker")
    public void user_clicks_on_flight_tracker() {
        tracker.clickFlightTracker();
    }

    @And("user navigates to Scheduled tab")
    public void user_navigates_to_scheduled_tab() {
        tracker.clickScheduledTab();
    }

    @And("user enters {string} in Airport field")
    public void user_enters_in_airport_field(String airport) {
        tracker.enterAirport(airport);
    }

    @And("user clicks Search button")
    public void user_clicks_search_button() {
        tracker.clickSearchButton();
    }

    @Then("flight results should be displayed")
    public void flight_results_should_be_displayed() {
        Assert.assertTrue(tracker.areFlightsDisplayed(), "Flights are not displayed");
    }

    @When("user clicks on first flight View Details")
    public void user_clicks_on_first_flight_view_details() {
        tracker.clickFirstFlightDetails();
    }

    @Then("flight detail page should be displayed")
    public void flight_detail_page_should_be_displayed() {
        Assert.assertTrue(tracker.isFlightDetailPageDisplayed(), "Flight detail page not displayed");
    }
}