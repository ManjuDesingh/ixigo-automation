package com.ixigo.stepDefinition;

import com.ixigo.setup.BaseSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends BaseSteps {

    @Given("I open the ixigo application")
    public void open_application() {
        launchBrowser();
    }

    @When("I submit passenger details")
    public void submit_passenger_details() {
        System.out.println("Submitting passenger details...");
        // Your Selenium code here
    }

    @Then("passenger details should be submitted successfully")
    public void passenger_details_submission_verification() {
        System.out.println("Passenger details submitted successfully!");
        // Verification code here
        quitDriver();
    }
}
