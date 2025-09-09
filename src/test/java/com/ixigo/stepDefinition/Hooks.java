package com.ixigo.stepDefinition;

import com.ixigo.setup.BaseSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final BaseSteps baseSteps;

    public Hooks(BaseSteps baseSteps) {
        this.baseSteps = baseSteps;
        logger.debug("Hooks initialized with BaseSteps");
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        logger.info("=================================================================");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Scenario tags: {}", scenario.getSourceTagNames());
        logger.info("=================================================================");
        
        baseSteps.launchBrowser();
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        try {
            // Capture screenshot on failure
            if (scenario.isFailed()) {
                logger.warn("Scenario FAILED: {}", scenario.getName());
                baseSteps.captureScreenshot(scenario.getName());
                
                // Optional: Log page source for debugging
                baseSteps.logPageSourceOnFailure();
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
            }
            
            logger.info("Ending scenario: {} - Status: {}", 
                       scenario.getName(), scenario.getStatus());
            
        } catch (Exception e) {
            logger.error("Error during teardown for scenario: {}", scenario.getName(), e);
        } finally {
            // Ensure driver is always quit even if previous steps fail
            baseSteps.quitDriver();
            
            logger.info("=================================================================");
            logger.info("Scenario completed: {}", scenario.getName());
            logger.info("=================================================================");
        }
    }

    // Tag-specific hooks with proper ordering
    @Before(value = "@mobile", order = 10)
    public void setupMobileBrowser(Scenario scenario) {
        logger.info("Setting up mobile browser configuration for scenario: {}", scenario.getName());
        baseSteps.configureMobileBrowser();
    }

    @Before(value = "@api", order = 10)
    public void setupApiTest(Scenario scenario) {
        logger.info("Setting up API test configuration for scenario: {}", scenario.getName());
        baseSteps.configureApiTest();
    }

    @After(value = "@screenshot", order = 10)
    public void takeScreenshotForTag(Scenario scenario) {
        logger.info("Taking screenshot for @screenshot tagged scenario: {}", scenario.getName());
        baseSteps.captureScreenshot("TAGGED_" + scenario.getName());
    }

    @After(value = "@cleanup-database", order = 5)
    public void cleanupDatabase(Scenario scenario) {
        logger.info("Cleaning up database for scenario: {}", scenario.getName());
        baseSteps.cleanupTestData();
    }

    // Global setup/teardown hooks
    @Before(order = Integer.MIN_VALUE)
    public void globalSetup(Scenario scenario) {
        logger.debug("Global setup before all hooks");
    }

    @After(order = Integer.MAX_VALUE)
    public void globalTeardown(Scenario scenario) {
        logger.debug("Global teardown after all hooks");
    }
}