package com.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;

public class FlightTrackerPage {
    WebDriver driver;
    ExtentTest test;

    // Locators
    private By flightTrackerBtn = By.xpath("//*[text()='Flight Tracker']");
    private By scheduledTab = By.xpath("//*[text()='Scheduled']");
    private By airportInput = By.xpath("//input[@data-testid='airline-code' or (@required and contains(@class,'outline-none'))]");
    private String dropdownOption = "//span[contains(text(),'%s')]";
    private By searchBtn = By.xpath("//*[@id='__next']//form/button[contains(.,'Search')]");
    private By firstFlightViewDetails = By.xpath("//p[text()='View Details']");
    private By flightResultBlock = By.xpath("//*[@id='__next']//div[contains(@class,'flight-list')]");

    public FlightTrackerPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
    }

    public void clickFlightTracker() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement flightTracker = wait.until(ExpectedConditions.elementToBeClickable(flightTrackerBtn));
        flightTracker.click();
    }

    public void clickScheduledTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement scheduled = wait.until(ExpectedConditions.elementToBeClickable(scheduledTab));
        scheduled.click();
    }

    public void enterAirport(String airport) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        boolean typed = false;
        int attempts = 0;

        while (!typed && attempts < 3) {
            try {
                attempts++;

                // Fresh locate for click
                WebElement airportField = wait.until(ExpectedConditions.presenceOfElementLocated(airportInput));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", airportField);
                wait.until(ExpectedConditions.elementToBeClickable(airportInput)).click();

                // Fresh locate for clear
                airportField = wait.until(ExpectedConditions.presenceOfElementLocated(airportInput));
                airportField.clear();

                // Fresh locate for sendKeys
                airportField = wait.until(ExpectedConditions.presenceOfElementLocated(airportInput));
                airportField.sendKeys(airport);

                // Select dropdown option
                By optionLocator = By.xpath(String.format(dropdownOption, airport));
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                option.click();

                typed = true;

            } catch (StaleElementReferenceException e) {
                System.out.println("DEBUG: Retrying enterAirport due to stale element (attempt " + attempts + ")");
            }
        }

        if (!typed) {
            throw new RuntimeException("Failed to type airport after retries due to stale element");
        }
    }

    public void clickSearchButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));

        try {
            search.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", search);
        }

        // Wait for results block or View Details
        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(firstFlightViewDetails),
            ExpectedConditions.presenceOfElementLocated(flightResultBlock)
        ));
    }

    public boolean areFlightsDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(firstFlightViewDetails));
            return driver.findElements(firstFlightViewDetails).size() > 0;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickFirstFlightDetails() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement details = wait.until(ExpectedConditions.elementToBeClickable(firstFlightViewDetails));
        details.click();
    }

    public boolean isFlightDetailPageDisplayed() {
        // check for detail modal or header
        try {
            By detailHeader = By.xpath("//*[contains(text(),'Flight Details') or contains(text(),'Flight Status')]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(detailHeader));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}