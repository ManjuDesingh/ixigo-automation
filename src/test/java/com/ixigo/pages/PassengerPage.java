package com.ixigo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * PassengerPage - handles passenger information entry during booking
 */
public class PassengerPage extends BasePage {

    private WebDriver driver;

    // ---------- Locators ----------
    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "gender")
    private WebElement genderDropdown;

    @FindBy(id = "dob")
    private WebElement dobField;

    @FindBy(id = "seatPreference")
    private WebElement seatDropdown;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "continueBtn")
    private WebElement continueButton;

    // ---------- Constructor ----------
    public PassengerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Actions ----------
    public void enterFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void selectGender(String gender) {
        Select select = new Select(genderDropdown);
        select.selectByVisibleText(gender);
    }

    public void enterDateOfBirth(String dob) {
        dobField.clear();
        dobField.sendKeys(dob);
    }

    public void selectSeat(String seatType) {
        Select select = new Select(seatDropdown);
        select.selectByVisibleText(seatType);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void clickContinue() {
        continueButton.click();
    }

    // ---------- Helper method for full submission ----------
    public boolean submitPassengerDetails(String firstName, String lastName, String gender,
                                          String dob, String seat, String email, String phone) {
        try {
            enterFirstName(firstName);
            enterLastName(lastName);
            selectGender(gender);
            enterDateOfBirth(dob);
            selectSeat(seat);
            enterEmail(email);
            enterPhone(phone);
            clickContinue();

            // Optionally, wait/check for next page element to confirm submission
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- Overloaded method for Cucumber step separation ----------
    public boolean submitPassengerDetails() {
        try {
            clickContinue();
            // Optionally, wait/check for next page element to confirm submission
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
