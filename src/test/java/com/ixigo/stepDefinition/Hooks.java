package com.ixigo.stepDefinition;

import com.ixigo.setup.BaseSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private BaseSteps baseSteps;

    public Hooks() {
        this.baseSteps = new BaseSteps();
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        baseSteps.launchBrowser(); // This method from your BaseSteps initializes the driver
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        System.out.println("Ending scenario: " + scenario.getName() + " Status: " + scenario.getStatus());
        BaseSteps.quitDriver(); // This method from the improved BaseSteps quits the driver
    }
}