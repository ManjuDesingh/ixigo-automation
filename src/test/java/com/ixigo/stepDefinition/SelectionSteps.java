package com.ixigo.stepDefinition;

import com.ixigo.pages.FlightSelectionPage;
import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SelectionSteps {

    private WebDriver driver;
    private FlightSelectionPage flightSelectionPage;

    public SelectionSteps() {
        this.driver = BaseSteps.getDriver(); // This will now work!
        this.flightSelectionPage = new FlightSelectionPage(driver);
    }

    @When("user selects the first available flight")
    public void user_selects_the_first_available_flight() {
        Assert.assertTrue(flightSelectionPage.isFlightListDisplayed(), "No flights are displayed for selection");
        flightSelectionPage.selectFirstAvailableFlight();
        ReporterUtil.logInfo("Selected the first available flight");
    }

    @When("user selects the flight at index {int}")
    public void user_selects_flight_at_index(int index) {
        Assert.assertTrue(flightSelectionPage.isFlightListDisplayed(), "No flights are displayed for selection");
        flightSelectionPage.selectFlightByIndex(index);
        ReporterUtil.logInfo("Selected flight at index: " + index);
    }

    @Then("flight should be selected successfully")
    public void flight_should_be_selected_successfully() {
        ReporterUtil.logPass("Flight selection completed successfully");
    }
}