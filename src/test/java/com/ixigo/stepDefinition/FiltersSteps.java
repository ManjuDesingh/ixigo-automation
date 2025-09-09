package com.ixigo.stepDefinition;

import com.ixigo.pages.FiltersPage;
import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.java.en.*;

public class FiltersSteps {

    private BaseSteps baseSteps = new BaseSteps(); // create instance
    private FiltersPage filtersPage;

    @Given("User is on the search results page")
    public void user_is_on_search_results_page() {
        filtersPage = new FiltersPage(baseSteps.getDriver()); // use instance method
        ReporterUtil.logInfo("User is on search results page");
    }

    @When("User applies filter by price range {string} to {string}")
    public void user_applies_price_filter(String minPrice, String maxPrice) {
        filtersPage.setPriceRange(minPrice, maxPrice);
        ReporterUtil.logInfo("Applied price filter: " + minPrice + " - " + maxPrice);
    }

    @And("User applies filter by airline {string}")
    public void user_applies_airline_filter(String airline) {
        filtersPage.selectAirline(airline);
        ReporterUtil.logInfo("Applied airline filter: " + airline);
    }

    @And("User applies filter by stops {string}")
    public void user_applies_stops_filter(String stops) {
        filtersPage.selectStops(stops);
        ReporterUtil.logInfo("Applied stops filter: " + stops);
    }

    @Then("Filtered results should be displayed correctly")
    public void filtered_results_should_be_displayed() {
        boolean isFilteredCorrectly = filtersPage.verifyFiltersApplied();
        if(isFilteredCorrectly) {
            ReporterUtil.logPass("Filters applied correctly and results are verified");
        } else {
            ReporterUtil.logFail("Filters did not apply correctly");
        }
    }
}
