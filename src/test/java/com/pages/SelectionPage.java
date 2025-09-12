package com.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.objectrepository.Locators;
import com.parameters.Reporter;
import org.openqa.selenium.WebDriver;


public class SelectionPage extends Searchingpage {

    public SelectionPage(WebDriver driver, ExtentTest extTest) {
        super(driver, extTest);
    }

    public boolean loadPage() {
        openFlightsTab();
        handlePopupIfExists();
        selectRoundTrip();
        enterBoardingPlace("chennai");
        enterLandingPlace("Mumbai");
        setTravellersAndClass(1, 0, 1, "Economy");
        clickSearch();

        try {
            waiter.until(ExpectedConditions.visibilityOfElementLocated(Locators.resultsContainer));
            // attempt to close any blocking popup
            closePriceLockPopupIfPresent();
            Reporter.generateReport(browser, extentTest, Status.PASS, "The page has been landed");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to load page: " + e.getMessage());
            return false;
        }
    }

    /**
     * Try multiple known popup locators and click using JS fallback if necessary.
     */
    public void closePriceLockPopupIfPresent() {
        By[] popupLocators = new By[] {
                By.xpath("//*[@id='portal-root']/div/div[2]/div/button"),
                By.cssSelector("#portal-root div.bg-white button"),
                By.cssSelector("button.OnboardingSheetLottie_OnboardingSheetInternationalButton__CUHff"),
                By.xpath("//*[@id='portal-root']//button")
        };

        for (By pLoc : popupLocators) {
            try {
                WebElement btn = new org.openqa.selenium.support.ui.WebDriverWait(browser, Duration.ofSeconds(3))
                        .until(ExpectedConditions.presenceOfElementLocated(pLoc));
                try {
                    WebElement clickable = new org.openqa.selenium.support.ui.WebDriverWait(browser, Duration.ofSeconds(2))
                            .until(ExpectedConditions.elementToBeClickable(pLoc));
                    clickable.click();
                    Reporter.generateReport(browser, extentTest, Status.INFO, "Popup dismissed using locator: " + pLoc);
                    return;
                } catch (Exception clickEx) {
                    try {
                        ((JavascriptExecutor) browser).executeScript("arguments[0].click();", btn);
                        Reporter.generateReport(browser, extentTest, Status.INFO, "Popup JS-clicked using locator: " + pLoc);
                        return;
                    } catch (Exception jsEx) {
                        try {
                            ((JavascriptExecutor) browser).executeScript(
                                    "arguments[0].style.opacity = 1; arguments[0].style.pointerEvents='auto'; arguments[0].style.visibility='visible';",
                                    btn);
                            ((JavascriptExecutor) browser).executeScript("arguments[0].click();", btn);
                            Reporter.generateReport(browser, extentTest, Status.INFO,
                                    "Popup forced-visible + clicked for locator: " + pLoc);
                            return;
                        } catch (Exception forceEx) {
                            // try next locator
                        }
                    }
                }
            } catch (Exception e) {
                // locator not present -> try next
            }
        }

        Reporter.generateReport(browser, extentTest, Status.INFO, "Price-lock popup not found or could not be dismissed");
    }

    /**
     * Generic text-based filter (Non-Stop, Airline names etc.)
     */
    public boolean applyFilter(String filterName) {
        if (filterName == null) filterName = "";
        String normalized = filterName.trim();
        closePriceLockPopupIfPresent();

        String lower = normalized.toLowerCase();
        String xpath = "//*[self::label or self::button or self::span or self::div or self::a]"
                + "[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + lower + "')]";

        By filterLocator = By.xpath(xpath);

        int attempts = 2;
        for (int i = 0; i < attempts; i++) {
            try {
                WebElement el = waiter.until(ExpectedConditions.presenceOfElementLocated(filterLocator));
                try {
                    ((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
                } catch (Exception ignore) {}
                try {
                    waiter.until(ExpectedConditions.elementToBeClickable(filterLocator)).click();
                    Reporter.generateReport(browser, extentTest, Status.PASS, "Filter clicked: " + normalized);
                    return true;
                } catch (Exception clickEx) {
                    try {
                        ((JavascriptExecutor) browser).executeScript("arguments[0].click();", el);
                        Reporter.generateReport(browser, extentTest, Status.PASS, "Filter JS-clicked: " + normalized);
                        return true;
                    } catch (Exception jsEx) {
                        // continue retry
                    }
                }
            } catch (Exception e) {
                try { Thread.sleep(250); } catch (InterruptedException ie) { /* ignore */ }
            }
        }

        // fallback: type into any filter-search box if available
        try {
            List<WebElement> boxes = browser.findElements(By.xpath(
                    "//input[contains(@placeholder,'Search') or contains(@aria-label,'search') or contains(@data-testid,'filter-search')]"));
            if (!boxes.isEmpty()) {
                WebElement box = boxes.get(0);
                box.clear();
                box.sendKeys(normalized);
                Thread.sleep(300);
                box.sendKeys(Keys.ENTER);
                WebElement match = waiter.until(ExpectedConditions.elementToBeClickable(filterLocator));
                try {
                    match.click();
                    Reporter.generateReport(browser, extentTest, Status.PASS, "Filter selected after typing: " + normalized);
                    return true;
                } catch (Exception ex) {
                    ((JavascriptExecutor) browser).executeScript("arguments[0].click();", match);
                    Reporter.generateReport(browser, extentTest, Status.PASS, "Filter JS-clicked after typing: " + normalized);
                    return true;
                }
            }
        } catch (Exception ignore) {}

        Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to select filter: " + filterName);
        return false;
    }

    /**
     * Apply Departure filter 06:00-12:00 (MORNING) using input[name='takeOff'][value='MORNING'].
     */
    public boolean applyDepartureMorning() {
        closePriceLockPopupIfPresent();
        try {
            By departureCheckbox = By.xpath("//input[@name='takeOff' and @value='MORNING']");
            WebElement el = waiter.until(ExpectedConditions.presenceOfElementLocated(departureCheckbox));
            ((JavascriptExecutor) browser).executeScript("arguments[0].click();", el);
            Reporter.generateReport(browser, extentTest, Status.PASS, "Departure filter applied: 06:00-12:00 (MORNING)");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Could not apply Departure (MORNING) filter: " + e.getMessage());
            return false;
        }
    }

    /**
     * Apply Arrival filter 06:00-12:00 (MORNING) using input[name='landing'][value='MORNING'].
     */
    public boolean applyArrivalMorning() {
        closePriceLockPopupIfPresent();
        try {
            By arrivalCheckbox = By.xpath("//input[@name='landing' and @value='MORNING']");
            WebElement el = waiter.until(ExpectedConditions.presenceOfElementLocated(arrivalCheckbox));
            ((JavascriptExecutor) browser).executeScript("arguments[0].click();", el);
            Reporter.generateReport(browser, extentTest, Status.PASS, "Arrival filter applied: 06:00-12:00 (MORNING)");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(browser, extentTest, Status.FAIL, "Could not apply Arrival (MORNING) filter: " + e.getMessage());
            return false;
        }
    }

    // Delegating helpers (keeps step code simple)
    public boolean applyAirlineFilter(String airlineName) {
        return applyFilter(airlineName);
    }

    public boolean selectFirstAvailableFlight() {
        closePriceLockPopupIfPresent();

        By[] candidateXPaths = new By[] {
                By.xpath("(//button[contains(.,'Select') or contains(.,'Book') or contains(.,'Continue')])[1]"),
                By.xpath("(//a[contains(.,'Select') or contains(.,'Book')])[1]"),
                By.xpath("(//button[contains(@data-testid,'select') or contains(@class,'select')])[1]"),
                By.xpath("(//div[contains(@class,'FlightCard') or contains(@class,'result-card')])[1]//button")
        };

        for (By cand : candidateXPaths) {
            try {
                WebElement el = waiter.until(ExpectedConditions.elementToBeClickable(cand));
                el.click();
                Reporter.generateReport(browser, extentTest, Status.PASS, "Clicked first available flight");
                return true;
            } catch (Exception ignore) {
                // try next candidate
            }
        }

        try {
            WebElement results = waiter.until(ExpectedConditions.visibilityOfElementLocated(Locators.resultsContainer));
            WebElement btn = results.findElement(By.xpath(".//button[.//text() or @aria-label][1]"));
            waiter.until(ExpectedConditions.elementToBeClickable(btn)).click();
            Reporter.generateReport(browser, extentTest, Status.PASS, "Clicked first available flight (fallback)");
            return true;
        } catch (Exception ignore) {}

        Reporter.generateReport(browser, extentTest, Status.FAIL, "Failed to select first available flight");
        return false;
    }
}