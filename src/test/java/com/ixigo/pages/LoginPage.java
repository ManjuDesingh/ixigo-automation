package com.ixigo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private WebDriver driver;

    // ---------- Constructor ----------
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Page Actions for Mobile Login ----------
    public void navigateToLoginPage() {
        driver.get("https://www.ixigo.com/");
        // click login modal button if needed
    }

    public boolean isLoginModalDisplayed() {
        return driver.findElement(By.id("loginModal")).isDisplayed();
    }

    public void clickMobileLoginOption() {
        driver.findElement(By.id("mobileLoginOption")).click();
    }

    public void enterMobileNumber(String mobile) {
        WebElement mobileField = driver.findElement(By.id("mobileNumber"));
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    public void clickContinueButton() {
        driver.findElement(By.id("continueBtn")).click();
    }

    public String getMobileErrorText() {
        try {
            return driver.findElement(By.xpath("//div[@class='error-msg']")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ---------- Optional: Username/Password Login ----------
    public void enterUsername(String username) {
        driver.findElement(By.id("username")).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(By.id("loginBtn")).click();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(By.xpath("//div[@class='error-msg']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
