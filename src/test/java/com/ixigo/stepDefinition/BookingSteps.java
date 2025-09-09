package com.ixigo.stepDefinition;

import com.ixigo.pages.*;
import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

public class BookingSteps {

    private WebDriver driver;
    private SearchPage searchPage;
    private FlightSelectionPage selectionPage;
    private PassengerPage passengerPage;

    @Given("the user launches the browser and is on the Ixigo homepage")
    public void launch_browser_and_homepage() {
        BaseSteps.launchBrowser();
        driver = BaseSteps.getDriver();
        initializePages();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("ixigo.com"), "Not on Ixigo homepage");
        ReporterUtil.logInfo("Browser launched and user is on Ixigo homepage");
    }

    @When("the user searches for flights from {string} to {string}")
    public void search_flights(String from, String to) {
        searchPage.enterFromCity(from);
        searchPage.enterToCity(to);
        ReporterUtil.logInfo("Entered flight route: " + from + " to " + to);
    }

    @When("the user selects departure and return dates")
    public void select_dates() {
        searchPage.selectDepartureDate();
        searchPage.selectReturnDate();
        ReporterUtil.logInfo("Selected departure and return dates");
    }

    @When("the user clicks on search")
    public void click_search() {
        searchPage.clickSearch();
        ReporterUtil.logInfo("Initiated flight search");
    }

    @Then("flight search results should be displayed")
    public void verify_search_results() {
        Assert.assertTrue(selectionPage.isResultsDisplayed(), "Flight search results not displayed");
        ReporterUtil.logPass("Flight search results displayed successfully");
    }

    @When("the user enters passenger details")
    public void enter_passenger_details(DataTable passengerTable) {
        List<Map<String, String>> passengers = passengerTable.asMaps(String.class, String.class);
        
        passengers.forEach(passenger -> {
            passengerPage.fillPassengerDetails(
                passenger.get("FirstName"),
                passenger.get("LastName"),
                passenger.get("Gender"),
                passenger.get("DateOfBirth")
            );
            ReporterUtil.logInfo("Added passenger: " + passenger.get("FirstName") + " " + passenger.get("LastName"));
        });
    }

    @When("the user selects seat preferences")
    public void select_seat_preferences(DataTable seatTable) {
        List<Map<String, String>> seatPreferences = seatTable.asMaps(String.class, String.class);
        
        seatPreferences.forEach(seat -> {
            passengerPage.selectSeat(seat.get("SeatType"));
            ReporterUtil.logInfo("Selected seat preference: " + seat.get("SeatType"));
        });
    }

    @When("the user adds contact information")
    public void add_contact_info(DataTable contactTable) {
        Map<String, String> contact = contactTable.asMap(String.class, String.class);
        
        passengerPage.enterEmail(contact.get("Email"));
        passengerPage.enterPhone(contact.get("Phone"));
        ReporterUtil.logInfo("Contact information added - Email: " + contact.get("Email") + ", Phone: " + contact.get("Phone"));
    }

    @Then("passenger details should be submitted successfully")
    public void verify_passenger_details_submission() {
        Assert.assertTrue(passengerPage.arePassengerDetailsSubmitted(), "Passenger details submission failed");
        ReporterUtil.logPass("Passenger details submitted successfully");
    }

    // Private helper method for page initialization
    private void initializePages() {
        searchPage = new SearchPage(driver);
        selectionPage = new FlightSelectionPage(driver);
        passengerPage = new PassengerPage(driver);
    }
}