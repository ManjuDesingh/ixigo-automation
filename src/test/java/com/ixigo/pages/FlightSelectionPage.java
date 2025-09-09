package com.ixigo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

/**
 * FlightSelectionPage - handles selecting flights from search results
 */
public class FlightSelectionPage extends BasePage {

    private WebDriver driver;

    // ---------- Locators ----------
    @FindBy(xpath = "//div[@class='flight-card']")
    private List<WebElement> flightCards;

    @FindBy(xpath = ".//button[contains(text(),'Select')]")
    private WebElement selectButton;

    // ---------- Constructor ----------
    public FlightSelectionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Actions ----------
    public void selectFirstAvailableFlight() {
        if (!flightCards.isEmpty()) {
            WebElement firstFlight = flightCards.get(0);
            firstFlight.findElement(By.xpath(".//button[contains(text(),'Select')]")).click();
        } else {
            throw new RuntimeException("No flights available to select");
        }
    }

    public void selectFlightByIndex(int index) {
        if (index >= 0 && index < flightCards.size()) {
            WebElement flight = flightCards.get(index);
            flight.findElement(By.xpath(".//button[contains(text(),'Select')]")).click();
        } else {
            throw new IllegalArgumentException("Invalid flight index: " + index);
        }
    }

    public boolean isFlightListDisplayed() {
        return !flightCards.isEmpty();
    }
}
