package com.pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.objectrepository.Locators;
import com.parameters.Reporter;

public class AirportCabsPage {
    WebDriver driver;
    ExtentTest test;
    private final Duration WAIT = Duration.ofSeconds(20);

    public AirportCabsPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
    }

    public void clickAirportCabsAndSwitch() {
        String parent = driver.getWindowHandle();
        driver.findElement(Locators.airportCabs).click();
        Reporter.generateReport(driver, test, Status.INFO, "Clicked Airport Cabs link");

        WebDriverWait wait = new WebDriverWait(driver, WAIT);
        try { wait.until(d -> d.getWindowHandles().size() > 1); } catch (Exception ignored) {}

        for (String h : driver.getWindowHandles()) {
            if (!h.equals(parent)) {
                driver.switchTo().window(h);
                Reporter.generateReport(driver, test, Status.INFO, "Switched to cab site: " + driver.getCurrentUrl());
                break;
            }
        }
    }

    public void selectHomeToAirport() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT);
        // simple stable locator based on the wrapper you provided
        By homeLabel = By.xpath("//*[@id='ctn']/div[1]/div/label[1]");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(homeLabel));
        el.click();
        Reporter.generateReport(driver, test, Status.PASS, "Selected Home To Airport");
    }

    
    public void enterPickupLocation(String fromText) {
        Reporter.generateReport(driver, test, Status.INFO, "Entering pickup location: " + fromText);

        WebDriverWait wait = new WebDriverWait(driver, WAIT);
        WebElement fromInput = null;

        // 1) Try presence in main DOM
        try {
            fromInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("from-input")));
        } catch (TimeoutException e) {
            Reporter.generateReport(driver, test, Status.INFO, "#from-input not present in main DOM (timeout). Will try wrapper and JS.");
        }

        // 2) If not found or not displayed, try clicking the wrapper to open the control
        if (fromInput == null || !fromInput.isDisplayed()) {
            try {
                By wrapper = By.xpath("//*[@id='ctn']/div[2]/div[1]");
                WebElement wrap = driver.findElement(wrapper);
                // scroll and click wrapper (click often reveals the input)
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wrap);
                try { wrap.click(); } catch (Exception e) { ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wrap); }
                Thread.sleep(500); // brief pause for UI to update
                // try to get input again
                try {
                    fromInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("from-input")));
                } catch (Exception ignored) {}
            } catch (Exception e) {
                Reporter.generateReport(driver, test, Status.INFO, "Wrapper click did not reveal input: " + e.getMessage());
            }
        }

        // 3) If still not found or not displayed, attempt JS set on document.getElementById
        if (fromInput == null || !fromInput.isDisplayed()) {
            try {
                Boolean jsSet = (Boolean) ((JavascriptExecutor) driver).executeScript(
                    "var el = document.getElementById('from-input'); if(el){ el.focus(); el.value = arguments[0]; el.dispatchEvent(new Event('input',{bubbles:true})); el.dispatchEvent(new Event('change',{bubbles:true})); return true; } return false;",
                    fromText);
                if (Boolean.TRUE.equals(jsSet)) {
                    Reporter.generateReport(driver, test, Status.PASS, "Set #from-input via JS in main document");
                    // attempt to select suggestion quickly
                    try {
                        By suggestion = By.xpath("//ul[contains(@class,'auto-from')]//li[contains(normalize-space(.), '" + fromText + "')]");
                        WebElement sug = wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
                        sug.click();
                        Reporter.generateReport(driver, test, Status.PASS, "Picked suggestion via JS-set");
                        return;
                    } catch (Exception ex) {
                        // fallback to ENTER
                        ((JavascriptExecutor) driver).executeScript("var e = document.getElementById('from-input'); if(e) { e.dispatchEvent(new KeyboardEvent('keydown', {key:'Enter'})); }");
                        Reporter.generateReport(driver, test, Status.INFO, "JS set done; attempted ENTER fallback");
                        return;
                    }
                }
            } catch (Exception e) {
                Reporter.generateReport(driver, test, Status.INFO, "JS attempt failed: " + e.getMessage());
            }
        }

        // 4) If we have a Selenium element and it's not visible, try forcing styles to visible then use it
        if (fromInput != null) {
            try {
                if (!fromInput.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.opacity=1; arguments[0].style.zIndex=9999;",
                        fromInput);
                }
            } catch (Exception ignored) {}
            try {
                fromInput.clear();
            } catch (Exception ignored) {}
            try {
                fromInput.sendKeys(fromText);
            } catch (Exception e) {
                // fallback to JS set on this element
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input',{bubbles:true})); arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                        fromInput, fromText);
                } catch (Exception jsEx) {}
            }

            // try to pick suggestion
            try {
                By suggestion = By.xpath("//ul[contains(@class,'auto-from')]//li[contains(normalize-space(.), '" + fromText + "')]");
                WebElement sug = wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
                sug.click();
                Reporter.generateReport(driver, test, Status.PASS, "Selected pickup suggestion: " + fromText);
                return;
            } catch (Exception ex) {
                // fallback Enter
                try { fromInput.sendKeys(Keys.ENTER); } catch (Exception ignored) {}
                Reporter.generateReport(driver, test, Status.INFO, "Sent ENTER as fallback after typing pickup");
                return;
            }
        }

        // 5) NOT FOUND: save screenshot and throw clear error
        takeScreenshot("pickup_input_failure");
        Reporter.generateReport(driver, test, Status.FAIL, "Could not locate or set #from-input (tried presence, wrapper click, and JS).");
        throw new NoSuchElementException("Could not locate or set #from-input. Check target/pickup_input_failure.png for page screenshot.");
    }

    public void enterDropLocation(String toText) {
        Reporter.generateReport(driver, test, Status.INFO, "Entering drop location: " + toText);
        WebDriverWait wait = new WebDriverWait(driver, WAIT);
        WebElement toInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("to-input")));
        try {
            if (!toInput.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible';", toInput);
            }
        } catch (Exception ignored) {}
        toInput.clear();
        toInput.sendKeys(toText);
        try {
            By suggestion = By.xpath("//ul[@id='to-options']//li[contains(normalize-space(.), '" + toText + "')]");
            WebElement sug = wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));
            sug.click();
            Reporter.generateReport(driver, test, Status.PASS, "Selected drop suggestion: " + toText);
            return;
        } catch (Exception e) {
            try { toInput.sendKeys(Keys.ENTER); } catch (Exception ignored) {}
            Reporter.generateReport(driver, test, Status.INFO, "Submitted drop via ENTER fallback");
        }
    }

    public void clickSearchButton() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT);
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
            btn.click();
            Reporter.generateReport(driver, test, Status.PASS, "Clicked SEARCH");
        } catch (Exception e) {
            takeScreenshot("search_click_failure");
            throw new NoSuchElementException("SEARCH button not clickable");
        }
    }

    private void takeScreenshot(String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + File.separator + "target" + File.separator + name + ".png";
            FileUtils.copyFile(src, new File(path));
            Reporter.generateReport(driver, test, Status.INFO, "Saved screenshot: " + path);
        } catch (IOException ignored) {}
    }
}