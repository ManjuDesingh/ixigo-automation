package com.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.pages.SelectionPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Selectiondefinition {

    WebDriver driver = Hooks.driver;
    ExtentTest extTest = Hooks.extTest;
    SelectionPage selectionPage;

    @Given("the user is on the selection page")
    public void the_user_is_on_the_selection_page() {
        selectionPage = new SelectionPage(driver, extTest);
        boolean loaded = selectionPage.loadPage();
        Assert.assertTrue(loaded, "Page did not load successfully");
        System.out.println("The page has been loaded");
    }

    @Given("the user wants to apply the first filter recommended_filter as {string}")
    public void the_user_wants_to_apply_the_first_filter_recommended_filter_as(String filter) {
        boolean applied = selectionPage.applyFilter(filter);
        Assert.assertTrue(applied, "Failed to apply Recommended Filter: " + filter);
    }

    @Given("the the second filter is selected Airlines as {string}")
    public void the_the_second_filter_is_selected_airlines_as(String airline) {
        boolean applied = selectionPage.applyAirlineFilter(airline);
        Assert.assertTrue(applied, "Failed to apply Airline Filter: " + airline);
    }

    @Given("the user clicks third filter departure as {string}")
    public void the_user_clicks_third_filter_departure_as(String departure) {
        boolean applied = selectionPage.applyDepartureMorning();
        Assert.assertTrue(applied, "Failed to apply Departure Filter: " + departure);
    }

    @Given("the user clicks fourth filter arrival as {string}")
    public void the_user_clicks_fourth_filter_arrival_as(String arrival) {
        boolean applied = selectionPage.applyArrivalMorning();
        Assert.assertTrue(applied, "Failed to apply Arrival Filter: " + arrival);
    }

    @Then("select the first available flight")
    public void select_the_first_available_flight() {
        boolean selected = selectionPage.selectFirstAvailableFlight();
        Assert.assertTrue(selected, "Failed to select the first available flight");
    }
}