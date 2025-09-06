package com.ixigo.stepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.ixigo.setup.BaseSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookingSteps {
	
    WebDriver driver;

	 @Given("user is on the ixigo login page")
	    public void user_is_on_the_ixigo_login_page() throws InterruptedException {
		 driver = BaseSteps.driver;

	        // now driver won't be null
	        String expResult = "https://www.ixigo.com/";
	        String actResult = driver.getCurrentUrl();
	        Assert.assertEquals(actResult, expResult);

	        Thread.sleep(2000);
	  	}

	    @When("user selects the mobile number login option")
	    public void user_selects_the_mobile_number_login_option() {
	        // Code to select mobile number login option
	    }

	    @When("user enters mobile number {string}")
	    public void user_enters_mobile_number(String mobile) {
	        // Code to enter mobile number
	    }

	    @When("user clicks on the Continue button")
	    public void user_clicks_on_the_continue_button() {
	        // Code to click continue
	    }

	    @Then("user should receive OTP and login should be successful")
	    public void user_should_receive_otp_and_login_should_be_successful() {
	        // Code to validate OTP step (mock or skip real OTP)
	    }

	    @Then("user should be redirected to the ixigo home page")
	    public void user_should_be_redirected_to_the_ixigo_home_page() {
	        // Code to validate home page navigation
	    }

	    @Then("an error message {string} should be displayed")
	    public void an_error_message_should_be_displayed(String errorMessage) {
	        // Code to validate error message
	    }

}