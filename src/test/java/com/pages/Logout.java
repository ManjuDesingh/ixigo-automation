package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Logout {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public Logout(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // XPath for Log out button
    private By logoutButton = By.xpath("//div[contains(@class,'bg-primary border-b border-tertiary !py-0 w-full border-b border-tertiary bg-primary z-40 min-w-[1240px] px-20 lg:px-0')]//div[contains(@class,'flex justify-between mx-auto max-w-[1240px] relative mainContainer')]//div[contains(@class,'flex gap-40 items-center justify-end')]//div//p[contains(@class,'body-md flex group-[.list-lg]:body-lg text-primary')][normalize-space()='Log out']");

    // Method to click Log out
    public void clickLogout() {
        WebElement logoutElem = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutElem.click();
    }

    // Optional combined method
    public void logoutUser() {
        clickLogout();
    }
}
