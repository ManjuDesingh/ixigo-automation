package com.ixigo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * BookingSummaryPage - represents the Booking Summary screen
 * after a user selects flight(s) and enters passenger details.
 */
public class BookingSummaryPage extends BasePage {

    private WebDriver driver;

    // ---------- Locators ----------
    @FindBy(id = "bookingSummaryTitle")
    private WebElement bookingSummaryTitle;

    @FindBy(xpath = "//button[contains(text(),'Proceed to Payment')]")
    private WebElement proceedToPaymentBtn;

    @FindBy(xpath = "//span[@class='total-price']")
    private WebElement totalPrice;

    @FindBy(xpath = "//button[contains(text(),'Edit Booking')]")
    private WebElement editBookingBtn;

    // ---------- Constructor ----------
    public BookingSummaryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Actions ----------
    public String getBookingSummaryTitle() {
        return bookingSummaryTitle.getText();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public void clickProceedToPayment() {
        proceedToPaymentBtn.click();
    }

    public void clickEditBooking() {
        editBookingBtn.click();
    }
}
