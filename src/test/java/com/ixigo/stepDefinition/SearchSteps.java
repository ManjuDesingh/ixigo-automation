package com.ixigo.stepDefinition;

import com.ixigo.pages.SearchPage;
import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class SearchSteps {

    private static final Logger logger = LoggerFactory.getLogger(SearchSteps.class);
    private final WebDriver driver;
    private final SearchPage searchPage;
    private final WebDriverWait wait;

    public SearchSteps(BaseSteps baseSteps) {
        this.driver = baseSteps.getDriver();
        this.searchPage = new SearchPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        logger.debug("SearchSteps initialized");
    }

    @Given("user is on the flight search page")
    public void user_is_on_the_flight_search_page() {
        try {
            driver.get("https://www.ixigo.com/flights");
            ReporterUtil.logInfo("Navigated to flight search page");
            
            // Wait for page to load and verify title
            wait.until(ExpectedConditions.titleContains("Flights"));
            Assert.assertTrue(driver.getTitle().contains("Flights"), 
                "Flight search page not loaded. Actual title: " + driver.getTitle());
            
            logger.info("Successfully loaded flight search page");
            
        } catch (Exception e) {
            ReporterUtil.logError("Failed to load flight search page: " + e.getMessage());
            logger.error("Error loading flight search page", e);
            throw new RuntimeException("Failed to load flight search page", e);
        }
    }

    @When("user enters flight search details from {string} to {string} departing on {string} returning on {string}")
    public void user_enters_flight_search_details(String fromCity, String toCity, String departureDate, String returnDate) {
        try {
            logger.info("Entering flight search details - From: {}, To: {}, Depart: {}, Return: {}", 
                       fromCity, toCity, departureDate, returnDate);
            
            searchPage.enterFromCity(fromCity);
            searchPage.enterToCity(toCity);
            searchPage.selectDepartureDate(departureDate);
            
            if (!returnDate.equalsIgnoreCase("none")) {
                searchPage.selectReturnDate(returnDate);
            }
            
            searchPage.clickSearch();
            ReporterUtil.logInfo("Entered flight search details and clicked search");
            
        } catch (Exception e) {
            ReporterUtil.logError("Failed to enter search details: " + e.getMessage());
            logger.error("Error entering search details", e);
            throw new RuntimeException("Failed to enter search details", e);
        }
    }

    @When("user searches for one-way flight from {string} to {string} departing on {string}")
    public void user_searches_for_one_way_flight(String fromCity, String toCity, String departureDate) {
        try {
            logger.info("Entering one-way flight search details - From: {}, To: {}, Depart: {}", 
                       fromCity, toCity, departureDate);
            
            searchPage.selectOneWayTrip();
            searchPage.enterFromCity(fromCity);
            searchPage.enterToCity(toCity);
            searchPage.selectDepartureDate(departureDate);
            searchPage.clickSearch();
            
            ReporterUtil.logInfo("Entered one-way flight search details and clicked search");
            
        } catch (Exception e) {
            ReporterUtil.logError("Failed to enter one-way search details: " + e.getMessage());
            logger.error("Error entering one-way search details", e);
            throw new RuntimeException("Failed to enter one-way search details", e);
        }
    }

    @Then("search results should be displayed")
    public void search_results_should_be_displayed() {
        try {
            // Wait for search results to load
            wait.until(ExpectedConditions.visibilityOf(searchPage.getSearchResultsContainer()));
            
            Assert.assertTrue(searchPage.areSearchResultsDisplayed(), 
                "Search results are not displayed");
            
            int resultCount = searchPage.getSearchResultCount();
            ReporterUtil.logInfo("Search results displayed successfully. Found " + resultCount + " results");
            logger.info("Search results displayed with {} results", resultCount);
            
        } catch (Exception e) {
            ReporterUtil.logError("Search results verification failed: " + e.getMessage());
            logger.error("Error verifying search results", e);
            throw new RuntimeException("Search results verification failed", e);
        }
    }

    @Then("no search results message should be displayed")
    public void no_search_results_message_should_be_displayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchPage.getNoResultsMessage()));
            Assert.assertTrue(searchPage.isNoResultsMessageDisplayed(), 
                "No results message is not displayed");
            
            ReporterUtil.logInfo("No search results message displayed as expected");
            logger.info("No search results message displayed");
            
        } catch (Exception e) {
            ReporterUtil.logError("No results message verification failed: " + e.getMessage());
            logger.error("Error verifying no results message", e);
            throw new RuntimeException("No results message verification failed", e);
        }
    }

    @Then("error message for invalid dates should be displayed")
    public void error_message_for_invalid_dates_should_be_displayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchPage.getDateErrorElement()));
            Assert.assertTrue(searchPage.isDateErrorMessageDisplayed(), 
                "Date error message is not displayed");
            
            ReporterUtil.logInfo("Date validation error message displayed");
            logger.info("Date validation error message displayed");
            
        } catch (Exception e) {
            ReporterUtil.logError("Date error message verification failed: " + e.getMessage());
            logger.error("Error verifying date error message", e);
            throw new RuntimeException("Date error message verification failed", e);
        }
    }
}