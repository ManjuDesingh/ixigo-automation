package com.ixigo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.DataProvider;

/**
 * TestRunnerParallel - allows running Cucumber features in parallel using TestNG
 */
@CucumberOptions(
        features = "src/test/resources/features",         // Path to feature files
        glue = {"com.ixigo.stepDefinition"},             // Package with step definitions
        plugin = {"pretty", "html:target/cucumber-parallel-reports.html"}, // Reports
        monochrome = true
)
public class TestRunnerParallel extends AbstractTestNGCucumberTests {

    // Enables parallel execution of scenarios
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
