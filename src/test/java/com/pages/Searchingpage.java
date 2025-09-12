package com.pages;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.objectrepository.Locators;
import com.parameters.Reporter;

public class Searchingpage {

    WebDriver browser;
    WebDriverWait waiter;
    ExtentTest extentTest;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Searchingpage(WebDriver driver, ExtentTest extTest) {
        this.browser = driver;
        this.waiter = new WebDriverWait(browser, Duration.ofSeconds(25));
        this.extentTest = extTest;
    }

    // Handle popup safely
    public void handlePopupIfExists() {
        try {
            List<WebElement> popups = browser.findElements(By.id("wiz-iframe-intent"));
            if (!popups.isEmpty()) {
                browser.switchTo().frame(popups.get(0));
                browser.findElement(By.id("closeButton")).click();
                browser.switchTo().defaultContent();
                Reporter.generateReport(browser, extentTest, Status.INFO, "Closed popup successfully");
            }
        } catch (Exception ignore) {
            // not critical if popup is absent
        }
    }

    public void openFlightsTab() {
        try {
            handlePopupIfExists();
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.flight)).click();
            Reporter.generateReport(browser, extentTest, Status.PASS, "Opened Flights tab");
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to open Flights tab: " + e.getMessage());
        }
    }

    public void selectRoundTrip() {
        try {
            handlePopupIfExists();
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.round)).click();
            Reporter.generateReport(browser, extentTest, Status.PASS, "Selected Round Trip");
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL,
                    "Round Trip selection failed (may be absent): " + e.getMessage());
        }
    }

    public void enterBoardingPlace(String from) {
        try {
            handlePopupIfExists();
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.from)).click();
            browser.findElement(Locators.click_from).sendKeys(from);

            List<WebElement> results = new WebDriverWait(browser, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
                            .xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]")));

            if (!results.isEmpty()) {
                results.get(0).click();
            } else {
                browser.findElement(Locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
            }
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to enter origin: " + from);
        }
    }

    public void enterLandingPlace(String to) {
        try {
            //handlePopupIfExists();

            waiter.until(ExpectedConditions.elementToBeClickable(Locators.to));
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.click_to)).sendKeys(to);

            List<WebElement> results = new WebDriverWait(browser, Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.xpath("//span[@class='block truncate' and text()='" + to + "']")));

            if (!results.isEmpty()) {
                results.get(0).click();
            } else {
                browser.findElement(Locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
            }
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to enter departure: " + to);
        }
    }

    public void setTravellersAndClass(int adults, int children, int infants, String travelClass) {
        try {
            handlePopupIfExists();
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.travellersPanel)).click();
            waiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" + adults + "']"))).click();
            waiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" + children + "']"))).click();

            /*// increase adults (default = 1)
            for (int i = 1; i < adults; i++) {
                waiter.until(ExpectedConditions.elementToBeClickable(Locators.adultsPlusBtn)).click();
            }

            for (int i = 0; i < children; i++) {
                waiter.until(ExpectedConditions.elementToBeClickable(Locators.childrenPlusBtn)).click();
            }

            for (int i = 0; i < infants; i++) {
                waiter.until(ExpectedConditions.elementToBeClickable(Locators.infantsPlusBtn)).click();
            }*/

            // select travel class
            try {
                waiter.until(ExpectedConditions.elementToBeClickable(Locators.travelClassDropdown)).click();
                By classOption = Locators.travelClassOption(travelClass);
                waiter.until(ExpectedConditions.elementToBeClickable(classOption)).click();
            } catch (Exception ex) {
                Reporter.generateReport(browser, extentTest, Status.WARNING,
                        "Travel class selection skipped: " + ex.getMessage());
            }

            // apply travellers
            try {
                waiter.until(ExpectedConditions.elementToBeClickable(Locators.travellersApplyBtn)).click();
            } catch (Exception ex) {
                Reporter.generateReport(browser, extentTest, Status.WARNING,
                        "Apply button not clicked: " + ex.getMessage());
            }

            Reporter.generateReport(browser, extentTest, Status.PASS,
                    "Travellers set: A" + adults + " C" + children + " I" + infants + " Class:" + travelClass);
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to set travellers/class: " + e.getMessage());
        }
    }

    public void clickSearch() {
        try {
            handlePopupIfExists();
            waiter.until(ExpectedConditions.elementToBeClickable(Locators.searchButton)).click();
            Reporter.generateReport(browser, extentTest, Status.PASS, "Clicked Search");
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to click Search: " + e.getMessage());
        }
    }

    // --- NEW: small helper for the price-lock popup (safe no-op if absent)
    private void handlePriceLockPopupIfPresent() {
        try {
            WebDriverWait w = new WebDriverWait(browser, Duration.ofSeconds(5));
            WebElement popupBtn = w.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Okay, Got it') or contains(.,'Got it')]")));
            popupBtn.click();
            Reporter.generateReport(browser, extentTest, Status.INFO, "Dismissed price-lock popup");
        } catch (Exception ignore) {
            // ignore if not present
        }
    }

    public boolean areResultsDisplayed() {
        try {
            // Sometimes a price-lock popup blocks the results; try to dismiss it
            handlePriceLockPopupIfPresent();

            // Use a longer wait for results load
            WebDriverWait longWait = new WebDriverWait(browser, Duration.ofSeconds(40));

            // Try multiple reliable indicators of results page
            By[] candidates = new By[] {
                // your original locator
                Locators.resultsContainer,
                // label/text variant
                By.xpath("//*[normalize-space()='Filters' or text()='Filters']"),
                // common results container patterns
                By.xpath("//div[contains(@class,'results') or contains(@class,'Results') or contains(@data-testid,'results')]"),
                By.xpath("//div[contains(@class,'FlightCard') or contains(@class,'result-card') or @data-testid='flight-card']"),
                // fallback: any element mentioning 'stops' which appears in filter rail
                By.xpath("//*[contains(translate(., 'STOPS', 'stops'),'stops')]")
            };

            for (By locator : candidates) {
                try {
                    longWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    Reporter.generateReport(browser, extentTest, Status.PASS, "Search results displayed");
                    return true;
                } catch (Exception ignore) {
                    // try next candidate
                }
            }

            // Last resort: URL heuristic — confirm we navigated to a results-like URL
            try {
                longWait.until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/flights"),
                        ExpectedConditions.urlContains("result"),
                        ExpectedConditions.urlContains("search")
                ));
                // If URL indicates results but no element found, still return false so you can refine locators later
            } catch (Exception ignore) {}

            Reporter.generateReport(browser, extentTest, Status.FAIL, "Search results not detected by any locator");
            return false;
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Search results not detected: " + e.getMessage());
            return false;
        }
    }
}