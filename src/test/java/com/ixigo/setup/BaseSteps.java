package com.ixigo.setup;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.Scenario;

public class BaseSteps {
    private static final Logger logger = LoggerFactory.getLogger(BaseSteps.class);
    private WebDriver driver;

    public void launchBrowser() {
        try {
            // Your browser initialization logic
            logger.info("Launching browser...");
            // Initialize driver based on configuration
        } catch (Exception e) {
            logger.error("Failed to launch browser", e);
            throw new RuntimeException("Browser initialization failed", e);
        }
    }

    public void quitDriver() {
        try {
            if (driver != null) {
                logger.info("Quitting browser driver...");
                driver.quit();
                driver = null;
                logger.info("Browser driver quit successfully");
            }
        } catch (Exception e) {
            logger.warn("Error while quitting driver", e);
        }
    }

    public void captureScreenshot(String scenarioName) {
        try {
            if (driver != null && driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                logger.info("Screenshot captured for scenario: {}", scenarioName);
                // You can attach this to Cucumber scenario if needed
            }
        } catch (Exception e) {
            logger.warn("Failed to capture screenshot for scenario: {}", scenarioName, e);
        }
    }

    public void logPageSourceOnFailure() {
        try {
            if (driver != null) {
                String pageSource = driver.getPageSource();
                logger.debug("Page source on failure:\n{}", pageSource);
            }
        } catch (Exception e) {
            logger.warn("Failed to get page source", e);
        }
    }

    public void configureMobileBrowser() {
        logger.info("Configuring mobile browser emulation");
        // Mobile-specific configuration
    }

    public void configureApiTest() {
        logger.info("Configuring API test setup");
        // API test configuration
    }

    public void cleanupTestData() {
        logger.info("Cleaning up test data from database");
        // Database cleanup logic
    }

    public WebDriver getDriver() {
        return driver;
    }
}