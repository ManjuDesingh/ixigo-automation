package com.pages;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.objectrepository.Locators;
import com.parameters.Reporter;

public class Loginpage {

    WebDriver driver;
    WebDriverWait wait;
    ExtentTest extTest;

    public Loginpage(WebDriver driver, ExtentTest extTest) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.extTest = extTest;
    }

    // Click login button
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(Locators.loginbutton)).click();
    }

    // Enter mobile number
    public boolean enterMobileNumber(String mobile) {
        clickLoginButton();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.mobile)).sendKeys(mobile);
            Reporter.generateReport(driver, extTest, Status.PASS, "Mobile number entered");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter mobile number");
            return false;
        }
    }

    // Click continue
    public boolean clickContinueForMobile() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(Locators.continuebutton)).click();
            Reporter.generateReport(driver, extTest, Status.PASS, "Clicked Continue for mobile");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to click Continue");
            return false;
        }
    }

    // Enter OTP manually
    public boolean enterOtpManually(String otp) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.otpInputs)).sendKeys(otp);
            Reporter.generateReport(driver, extTest, Status.PASS, "OTP entered");
            return true;
        } catch (Exception e) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter OTP");
            return false;
        }
    }

    // Verify navigation to flight page
    public boolean navigatedPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='One Way']")));
            Reporter.generateReport(driver, extTest, Status.PASS, "Flight details displayed");
            return true;
        } catch (TimeoutException te) {
            Reporter.generateReport(driver, extTest, Status.FAIL, "Navigation failed");
            return false;
        }
    }

    // Handle invalid mobile numbers
    public void invalidNumber(String number) {
        clickLoginButton();
        WebElement mobileInput = wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.mobile));
        mobileInput.clear();
        mobileInput.sendKeys(number);
    }

    // Verify error messages
    public void verifyErrorMessage(String expectedMessage) {
        WebElement errorMsg = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + expectedMessage + "')]")));
        Assert.assertTrue(errorMsg.isDisplayed(), "Expected error not shown: " + expectedMessage);
    }

    // Load cookies from file
    public void loadCookiesFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] token = line.split(";", 7);
                if (token.length == 7) {
                    String name = token[0];
                    String value = token[1];
                    String domain = token[2];
                    String path = token[3];
                    Date expiry = token[4].isEmpty() ? null : new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(token[4]);
                    boolean isSecure = Boolean.parseBoolean(token[5]);
                    boolean isHttpOnly = Boolean.parseBoolean(token[6]);

                    Cookie.Builder builder = new Cookie.Builder(name, value)
                            .domain(domain)
                            .path(path)
                            .isSecure(isSecure)
                            .isHttpOnly(isHttpOnly);
                    if (expiry != null) builder.expiresOn(expiry);
                    driver.manage().addCookie(builder.build());
                }
            }
            driver.navigate().refresh();
            System.out.println("Cookies loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save cookies to file
    public void saveCookiesToFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.exists()) file.delete();
            file.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Cookie ck : driver.manage().getCookies()) {
                    bw.write(ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" +
                            (ck.getExpiry() != null ? ck.getExpiry().toString() : "") + ";" + ck.isSecure() + ";" +
                            ck.isHttpOnly());
                    bw.newLine();
                }
            }
            System.out.println("Cookies saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
