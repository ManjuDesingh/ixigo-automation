package com.objectrepository;

import org.openqa.selenium.By;

public class Locators {
    public static By loginbutton = By.xpath("//button[text()='Log in/Sign up']");
    public static By mobile = By.xpath("//input[@placeholder='Enter Mobile Number']");
    public static By continuebutton = By.xpath("//button[text()='Continue']");
    public static By otpInputs = By.xpath("//input[@type='tel' or @inputmode='numeric']");
    public static By verify = By.xpath("//*[text()='Verify' or normalize-space()='Verify']");
    public static By flight = By.xpath("//a[@href='/flights']");
    public static By round = By.xpath("//button[text()='Round Trip']");	
    public static By from = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div");
    public static By click_from = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input");
    public static By to = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div");
    public static By click_to = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input");
    public static By searchButton = By.xpath("//button[normalize-space()='Search' or contains(@class,'search')]");
    public static By travellersPanel = By.xpath("//div[contains(text(),'Travellers') or contains(@class,'traveller') or contains(@class,'passenger')]");
    public static By childrenPlusBtn = By.xpath("(//button[contains(.,'+')])[2]");
    public static By infantsPlusBtn = By.xpath("(//button[contains(.,'+')])[3]");
    public static By travellersApplyBtn = By.xpath("//button[normalize-space()='Apply' or normalize-space()='Done']");

    public static By travelClassDropdown = By.xpath("//div[contains(@class,'cabin') or contains(.,'Class') or contains(@data-testid,'cabin')]");
    public static By resultsContainer = By.xpath("//p[text()='Filters']");
    public static By dept = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div");
    public static By ret = By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div/div/div");

    public static By monthYearHeader = By.xpath("//div[contains(@class,'DayPicker-Caption')]");
    public static By calendarNextBtn = By.xpath("//span[@aria-label='Next Month']");
    public static By calendarPrevBtn = By.xpath("//span[@aria-label='Previous Month']");
    
    public static By dayCell(String day) {
        return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day + "']");
    }
    public static By dayBlock(String day) {
        return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day + "']/..");
    }
    public static By dayPrice(String day) {
        return By.xpath("//div[contains(@class,'DayPicker-Day') and not(contains(@class,'disabled'))]//p[text()='" + day + "']/following-sibling::p");
    }
    public static By yearLabel = By.xpath("//div[contains(@class,'DayPicker-Caption')]");

    public static By travelClassOption(String cls) {
        String xpath = String.format(
            "//li[normalize-space(.)='%s'] | " +
            "//div[contains(@class,'option') and normalize-space(.)='%s'] | " +
            "//button[normalize-space(.)='%s']",
            cls, cls, cls
        );
        return By.xpath(xpath);
    }

    // ------------------- Airport Cabs Locators -------------------

    // Airport Cabs button on search page
    public static By airportCabs = By.xpath("//a[@data-testid='submenu-item' and .//p[normalize-space()='Airport Cabs']]");

    // FIXED: Radio button - Home To Airport (handles both typo and correct spelling)
    public static By homeToAirport = By.xpath("//label[contains(.,'Home To Airport') or contains(.,'Home To Aiport')]");

    // From input
    public static By fromLocationInput = By.xpath("//input[@id='from-input' and @placeholder='Pick Up Location']");

    // Autocomplete option (example: Chennai International Airport - MAA)
    public static By fromSuggestionMAA = By.xpath("//ul[contains(@class,'auto-from')]//li[contains(.,'Chennai International Airport (MAA)')]");

    // To input
    public static By toLocationInput = By.xpath("//input[@id='to-input' and contains(@placeholder,'Search Nearest Airport')]");

    // Autocomplete option (example: Mumbai - CSMI Airport-T1)
    public static By toSuggestionBOM_T1 = By.xpath("//ul[@id='to-options']//li[contains(normalize-space(.),'Mumbai - CSMI Airport-T1')]");

    // Pickup Date container
    public static By pickupDateDisplay = By.xpath("//*[@id='display-date']");

    // Pickup Date hidden input (if available)
    public static By pickupDateHiddenInput = By.xpath("//input[@id='hidden-date-input']");

    // Pickup Time display
    public static By pickupTimeDisplay = By.xpath("//*[@id='displayed-time']");

    // Pickup Time hidden input
    public static By pickupTimeInput = By.xpath("//input[@id='pickup-time' and @type='time']");

    // Search button
    public static By searchCabsButton = By.xpath("//button[@id='submit-btn' and normalize-space()='SEARCH']");
}