package com.ixigo.objectRepository;

import org.openqa.selenium.By;

public class Locators {

    // 🔍 Search Page
    public static final By fromCity = By.id("fromCity");
    public static final By toCity = By.id("toCity");
    public static final By departureDate = By.id("departure");
    public static final By returnDate = By.id("return");
    public static final By searchButton = By.xpath("//button[contains(text(),'Search')]");

    // ✈️ Flight Selection Page
    public static final By flightOption = By.cssSelector(".flight-card");
    public static final By bookButton = By.xpath("//button[contains(text(),'Book')]");

    // 👤 Login Page
    public static final By usernameField = By.id("username");
    public static final By passwordField = By.id("password");
    public static final By loginButton = By.id("loginBtn");

    // 🛫 Passenger Page
    public static final By firstName = By.id("firstName");
    public static final By lastName = By.id("lastName");
    public static final By genderDropdown = By.id("gender");
    public static final By continueButton = By.id("continueBtn");

    // 📄 Booking Summary Page
    public static final By summaryTitle = By.xpath("//h1[contains(text(),'Booking Summary')]");
    public static final By confirmBooking = By.xpath("//button[contains(text(),'Confirm')]");

    // 🚪 Logout Page
    public static final By profileIcon = By.id("profileMenu");
    public static final By logoutButton = By.xpath("//a[contains(text(),'Logout')]");
}
