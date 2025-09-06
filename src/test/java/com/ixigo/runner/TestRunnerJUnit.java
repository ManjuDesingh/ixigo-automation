package com.ixigo.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"com.ixigo.stepDefinition"}, // This is the crucial part
	    plugin = {"pretty", "html:target/cucumber-reports.html"},
	    monochrome = true
	)
public class TestRunnerJUnit extends AbstractTestNGCucumberTests{

}