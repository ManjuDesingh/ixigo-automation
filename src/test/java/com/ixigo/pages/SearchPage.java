package com.ixigo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class SearchPage {

    private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "fromCity")
    private WebElement fromCityInput;

    @FindBy(id = "toCity")
    private WebElement toCityInput;

    @FindBy(id = "departure")
    private WebElement departureDateInput;

    @FindBy(id = "return")
    private WebElement returnDateInput;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElement searchButton;

    @FindBy(className = "search-results")
    private WebElement searchResultsContainer;

    @FindBy(className = "no-results")
    private WebElement noResultsMessage;

    @FindBy(className = "date-error")
    private WebElement dateErrorElement;

    @FindBy(xpath = "//input[@value='oneway']")
    private WebElement oneWayRadio;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
        logger.debug("SearchPage initialized");
    }

    public void enterFromCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(fromCityInput));
        fromCityInput.clear();
        fromCityInput.sendKeys(city);
        logger.info("Entered from city: {}", city);
    }

    public void enterToCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(toCityInput));
        toCityInput.clear();
        toCityInput.sendKeys(city);
        logger.info("Entered to city: {}", city);
    }

    public void selectDepartureDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(departureDateInput));
        departureDateInput.clear();
        departureDateInput.sendKeys(date);
        logger.info("Selected departure date: {}", date);
    }

    public void selectReturnDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(returnDateInput));
        returnDateInput.clear();
        returnDateInput.sendKeys(date);
        logger.info("Selected return date: {}", date);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        logger.info("Clicked search button");
    }

    public void selectOneWayTrip() {
        wait.until(ExpectedConditions.elementToBeClickable(oneWayRadio));
        oneWayRadio.click();
        logger.info("Selected one-way trip");
    }

    public boolean areSearchResultsDisplayed() {
        try {
            return searchResultsContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getSearchResultCount() {
        // Implementation to count search results
        return 0; // Replace with actual implementation
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return noResultsMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDateErrorMessageDisplayed() {
        try {
            return dateErrorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getSearchResultsContainer() {
        return searchResultsContainer;
    }

    public WebElement getNoResultsMessage() {
        return noResultsMessage;
    }

    public WebElement getDateErrorElement() {
        return dateErrorElement;
    }
}