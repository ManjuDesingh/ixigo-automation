package com.stepdefinitions;

import com.pages.AirportCabsPage;
import com.parameters.Reporter;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import io.cucumber.java.After;

import static com.stepdefinitions.Hooks.driver;
import static com.stepdefinitions.Hooks.extTest;

public class AirportCabsdefinitions {

    AirportCabsPage cabsPage = new AirportCabsPage(driver, extTest);
    // store parent handle so we can return to it in @After
    private String parentWindowHandle;

    @Given("user is on the search page")
    public void user_is_on_the_search_page() {
        // keep consistent with your project: either read URL from config or use ixigo home
        parentWindowHandle = driver.getWindowHandle();
        driver.get("https://www.ixigo.com/");
        Reporter.generateReport(driver, extTest, Status.INFO, "User is on Ixigo search page");
    }

    @When("user clicks on Airport Cabs option")
    public void user_clicks_on_airport_cabs_option() {
        try {
            // store parent handle again in case navigation changed it
            parentWindowHandle = driver.getWindowHandle();
            cabsPage.clickAirportCabsAndSwitch();
            Reporter.generateReport(driver, extTest, Status.PASS, "Clicked Airport Cabs and switched to new tab/window");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to click Airport Cabs or switch window: " + e.getMessage());
            throw e;
        }
    }

    @When("user selects Home To Aiport option")
    public void user_selects_home_to_aiport_option() {
        try {
            cabsPage.selectHomeToAirport();
            Reporter.generateReport(driver, extTest, Status.PASS, "Selected Home To Airport option");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to select Home To Airport: " + e.getMessage());
            throw e;
        }
    }

    @When("user enters pickup location as {string}")
    public void user_enters_pickup_location_as(String from) {
        try {
            cabsPage.enterPickupLocation(from);
            Reporter.generateReport(driver, extTest, Status.PASS, "Entered pickup location: " + from);
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter pickup location '" + from + "': " + e.getMessage());
            throw e;
        }
    }

    @When("user enters drop location as {string}")
    public void user_enters_drop_location_as(String to) {
        try {
            cabsPage.enterDropLocation(to);
            Reporter.generateReport(driver, extTest, Status.PASS, "Entered drop location: " + to);
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter drop location '" + to + "': " + e.getMessage());
            throw e;
        }
    }

    @Then("user clicks on cab search button")
    public void user_clicks_on_cab_search_button() {
        try {
            cabsPage.clickSearchButton();
            Reporter.generateReport(driver, extTest, Status.PASS, "Clicked cab search button");
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to click cab search button: " + e.getMessage());
            throw e;
        }
    }

    @Then("cab search results should be displayed")
    public void cab_search_results_should_be_displayed() {
        try {
            // Basic validation: page contains some results container or keywords
            boolean found = driver.getPageSource().toLowerCase().contains("results")
                     || driver.getTitle().toLowerCase().contains("cab")
                     || driver.getCurrentUrl().toLowerCase().contains("search");

            if (found) {
                Reporter.generateReport(driver, extTest, Status.PASS, "Cab search results are displayed");
            } else {
                Reporter.generateReport(driver, extTest, Status.FAIL, "Cab search results are NOT displayed");
                throw new AssertionError("Cab search results not displayed");
            }
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Error while validating cab results: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Cleanup: close any child windows opened during the scenario and switch back to parent.
     * This runs after each scenario.
     */
    @After
    public void afterScenarioCleanup() {
        try {
            // If parentWindowHandle is null, try to set it
            if (parentWindowHandle == null) {
                try {
                    parentWindowHandle = driver.getWindowHandle();
                } catch (Exception ignored) {}
            }

            // Close any extra windows/tabs and switch back to parent
            for (String handle : driver.getWindowHandles()) {
                if (parentWindowHandle != null && !handle.equals(parentWindowHandle)) {
                    try {
                        driver.switchTo().window(handle);
                        driver.close();
                        Reporter.generateReport(driver, extTest, Status.INFO, "Closed child window: " + handle);
                    } catch (Exception e) {
                        Reporter.generateReport(driver, extTest, Status.INFO, "Could not close window " + handle + ": " + e.getMessage());
                    }
                }
            }

            // switch back to parent if available
            if (parentWindowHandle != null) {
                try {
                    driver.switchTo().window(parentWindowHandle);
                    Reporter.generateReport(driver, extTest, Status.INFO, "Switched back to parent window");
                } catch (Exception e) {
                    Reporter.generateReport(driver, extTest, Status.INFO, "Unable to switch back to parent window: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.INFO, "Exception in afterScenarioCleanup: " + e.getMessage());
        }
    }
}