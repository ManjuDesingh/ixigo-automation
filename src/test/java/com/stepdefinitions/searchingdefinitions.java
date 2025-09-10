package com.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.pages.Searchingpage;
import com.setup.Base;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class searchingdefinitions {

	WebDriver driver = Hooks.driver;
	ExtentTest extTest = Hooks.extTest;
	Searchingpage search;

	@Given("the user is on the homepage")
	public void the_user_is_on_the_homepage() {
		// assume Hooks setUp launched the site
		search = new Searchingpage(driver, extTest);
		String expected = Base.driver.getCurrentUrl(); // or you can assert specific URL if you want
	}

	@When("the user selects {string} trip type")
	public void the_user_selects_trip_type(String tripType) {
		// Only Round Trip handled for now
		if (tripType.equalsIgnoreCase("Round Trip")) {
			search.selectRoundTrip();
		}
	}

	@When("the user enters origin as {string}")
	public void the_user_enters_origin_as(String from) {
		search.enterBoardingPlace(from);
	}

	@When("the user enters destination as {string}")
	public void the_user_enters_destination_as(String to) {
		search.enterLandingPlace(to);
	}

	@When("the user sets travellers as {string} adults, {string} children, {string} infants and class as {string}")
	public void the_user_sets_travellers_and_class(String adults, String children, String infants,
			String travel_class) {
		int a = Integer.parseInt(adults.trim());
		int c = Integer.parseInt(children.trim());
		int i = Integer.parseInt(infants.trim());
		search.setTravellersAndClass(a, c, i, travel_class);
	}

	@When("the user clicks Search")
	public void the_user_clicks_search() {
		search.clickSearch();
	}

	@Then("search results should be displayed")
	public void search_results_should_be_displayed() {
		boolean res = search.areResultsDisplayed();
		Assert.assertTrue(res, "Expected search results to be displayed");
	}
}