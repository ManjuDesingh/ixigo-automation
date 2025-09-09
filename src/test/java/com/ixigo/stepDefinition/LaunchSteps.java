package com.ixigo.stepDefinition;

import com.ixigo.setup.BaseSteps;
import com.ixigo.parameters.ReporterUtil;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LaunchSteps {

    private BaseSteps baseSteps = new BaseSteps();
    private WebDriver driver;

    @Given("the user launches the browser")
    public void the_user_launches_the_browser() {
        baseSteps.launchBrowser();
        driver = baseSteps.getDriver();
        ReporterUtil.logInfo("Browser launched and URL opened successfully");
    }

    @Then("the Ixigo homepage should be displayed")
    public void the_ixigo_homepage_should_be_displayed() {
        Assert.assertTrue(driver.getCurrentUrl().contains("ixigo.com"), "Ixigo homepage not displayed!");
        ReporterUtil.logPass("Ixigo homepage displayed successfully");
    }
}
