package com.ixigo.stepDefinition;

import com.ixigo.pages.LogoutPage;
import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LogoutSteps {

    private WebDriver driver;
    private LogoutPage logoutPage;

    public LogoutSteps() {
        this.driver = BaseSteps.getDriver();
        this.logoutPage = new LogoutPage(driver);
    }

    @Given("user is logged in")
    public void user_is_logged_in() {
        Assert.assertTrue(logoutPage.isUserLoggedIn(), "User is not logged in.");
        ReporterUtil.logInfo("User is logged in successfully.");
    }

    @Then("user clicks on the logout button")
    public void user_clicks_on_logout_button() {
        logoutPage.clickLogout();
        ReporterUtil.logInfo("Logout button clicked.");
    }

    @Then("user should be logged out successfully")
    public void user_should_be_logged_out_successfully() {
        Assert.assertTrue(logoutPage.isUserLoggedOut(), "User was not logged out successfully.");
        ReporterUtil.logPass("User logged out successfully.");
    }
}
