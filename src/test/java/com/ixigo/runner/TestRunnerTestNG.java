package com.ixigo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestRunnerTestNG - runs all Cucumber features sequentially using TestNG
 */
@CucumberOptions(
        features = "src/test/resources/features",         // Path to feature files
        glue = {"com.ixigo.stepDefinition"},             // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-testng-reports.html"}, // HTML report
        monochrome = true                                // Makes console output readable
)
public class TestRunnerTestNG extends AbstractTestNGCucumberTests {

}
