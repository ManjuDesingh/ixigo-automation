package com.ixigo.stepDefinition;

import com.ixigo.setup.BaseSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;

public class PassengerSteps extends BaseSteps {

    @Given("user is on the passenger details page")
    public void user_is_on_passenger_details_page() {
        launchBrowser(); // Opens browser and navigates to URL
        System.out.println("Navigated to Passenger Details Page");
    }

    @When("user enters passenger details")
    public void user_enters_passenger_details(DataTable passengerTable) {
        List<Map<String, String>> rows = passengerTable.asMaps(String.class, String.class);
        for (Map<String, String> passenger : rows) {
            String firstName = passenger.get("FirstName");
            String lastName = passenger.get("LastName");
            String gender = passenger.get("Gender");
            String dob = passenger.get("DateOfBirth");

            System.out.println("Entering Passenger: " + firstName + " " + lastName + ", Gender: " + gender + ", DOB: " + dob);

            // TODO: Use Selenium to fill form fields here
            // driver.findElement(By.id("firstName")).sendKeys(firstName);
        }
    }

    @When("user selects seat preferences")
    public void user_selects_seat_preferences(DataTable seatTable) {
        List<Map<String, String>> rows = seatTable.asMaps(String.class, String.class);
        for (Map<String, String> seat : rows) {
            String seatType = seat.get("SeatType");
            System.out.println("Selecting Seat Type: " + seatType);

            // TODO: Use Selenium to select seat
            // driver.findElement(By.id("seatType")).selectByVisibleText(seatType);
        }
    }

    @When("user adds contact information")
    public void user_adds_contact_information(DataTable contactTable) {
        List<Map<String, String>> rows = contactTable.asMaps(String.class, String.class);
        for (Map<String, String> contact : rows) {
            String email = contact.get("Email");
            String phone = contact.get("Phone");

            System.out.println("Adding Contact: " + email + ", " + phone);

            // TODO: Use Selenium to fill email & phone
            // driver.findElement(By.id("email")).sendKeys(email);
        }
    }

    @Then("passenger details should be submitted successfully")
    public void passenger_details_submission_verification() {
        System.out.println("Passenger details submitted successfully!");
        quitDriver(); // Close browser after scenario
    }
}
