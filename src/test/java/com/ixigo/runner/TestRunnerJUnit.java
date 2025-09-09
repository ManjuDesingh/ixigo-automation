package com.ixigo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestRunnerJUnit - TestNG runner for executing all Cucumber features
 */
@CucumberOptions(
        features = "src/test/resources/features",         // Path to your feature files
        glue = {"com.ixigo.stepDefinition"},             // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Reports
        monochrome = true                                // Makes console output readable
)
public class TestRunnerJUnit extends AbstractTestNGCucumberTests {

}
