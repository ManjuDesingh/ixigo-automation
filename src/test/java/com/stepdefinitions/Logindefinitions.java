package com.stepdefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.pages.Loginpage;
import com.setup.Base;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Logindefinitions {

    WebDriver driver = Hooks.driver;
    ExtentTest extTest = Hooks.extTest;
    Loginpage loginPage; // declare

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        // ✅ initialize here for both positive & negative flows
        loginPage = new Loginpage(driver, extTest);

        String expRes = "https://www.ixigo.com/";
        String actRes = driver.getCurrentUrl();
        Assert.assertEquals(actRes, expRes, "User is not on Ixigo login page!");
        System.out.println("✅ The website has been launched successfully");
    }

    // --------- Negative flow ---------
    @When("the user enters invalid mobileno as {string}")
    public void the_user_enters_invalid_mobileno_as(String invalidMobile) {
        loginPage.invalidnumber(invalidMobile);
    }

    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        loginPage.clickContinueForMobile();
    }

    @Then("the system should display {string}")
    public void the_system_should_display(String expectedMessage) {
        loginPage.verifyErrorMessage(expectedMessage);
    }

    // --------- Positive flow ---------
    @When("the user enters mobileno as {string}")
    public void the_user_enters_mobileno_as(String mobileNo) {
        boolean actRes = loginPage.enterMobileNumber(mobileNo);
        Assert.assertTrue(actRes, "Failed to enter mobile number!");
        loginPage.clickContinueForMobile();
    }

    @When("enters the correct OTP")
    public void enters_the_correct_otp() {
        loginPage.enterOtpManually();
        Base.sleep();
    }

    @Then("the user should be navigated to the booking page")
    public void the_user_should_be_navigated_to_the_booking_page() {
        Assert.assertTrue(loginPage.navigatedpage(), "User not navigated to booking page");
    }
}