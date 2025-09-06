package com.ixigo.stepDefinition;

import com.ixigo.pages.LoginPage;
import com.ixigo.setup.BaseSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {

    WebDriver driver;
    LoginPage loginPage;

    public LoginSteps() {
        this.driver = BaseSteps.getDriver();
        this.loginPage = new LoginPage(driver); // You need to create this Page Object
    }

    @Given("user is on the ixigo login page")
    public void user_is_on_the_ixigo_login_page() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isLoginModalDisplayed(), "Login modal was not displayed.");
    }

    @When("user selects the mobile number login option")
    public void user_selects_the_mobile_number_login_option() {
        loginPage.clickMobileLoginOption();
    }

    @When("user enters mobile number {string}")
    public void user_enters_mobile_number(String mobileNumber) {
        loginPage.enterMobileNumber(mobileNumber);
    }

    @When("user clicks on the Continue button")
    public void user_clicks_on_the_continue_button() {
        loginPage.clickContinueButton();
    }


    @Then("user should be redirected to the ixigo home page")
    public void user_should_be_redirected_to_the_ixigo_home_page() {
        String expectedUrl = "https://www.ixigo.com/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "User was not redirected to the home page.");
    }

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getMobileErrorText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message did not match.");
    }
}