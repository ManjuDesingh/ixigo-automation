package com.ixigo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LogoutPage extends BasePage {

    private WebDriver driver;

    // ---------- Constructor ----------
    public LogoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Actions ----------
    public void clickProfileIcon() {
        driver.findElement(By.id("profileMenu")).click();
    }

    public void clickLogout() {
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
    }

    public void logout() {
        clickProfileIcon();
        clickLogout();
    }

    // ---------- Helper methods for verification ----------
    public boolean isUserLoggedIn() {
        try {
            return driver.findElement(By.id("profileMenu")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserLoggedOut() {
        try {
            // You can check that login button or login modal is visible
            return driver.findElement(By.id("loginModal")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
