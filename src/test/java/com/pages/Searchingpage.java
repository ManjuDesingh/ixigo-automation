package com.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.objectrepository.Locators;
import com.parameters.Reporter;

public class Searchingpage {

	WebDriver driver;
	WebDriverWait wait;
	ExtentTest extTest;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Searchingpage(WebDriver driver, ExtentTest extTest) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		this.extTest = extTest;
	}

	// Handle popup safely
	private void handlePopupIfExists() {
		try {
			List<WebElement> popups = driver.findElements(By.id("wiz-iframe-intent"));
			if (!popups.isEmpty()) {
				driver.switchTo().frame(popups.get(0));
				driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
				Reporter.generateReport(driver, extTest, Status.INFO, "Closed popup successfully");
			}
		} catch (Exception ignore) {
			// not critical if popup is absent
		}
	}


	public void openFlightsTab() {
		try {
			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(Locators.flight)).click();
			Reporter.generateReport(driver, extTest, Status.PASS, "Opened Flights tab");
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to open Flights tab: " + e.getMessage());
		}
	}

	public void selectRoundTrip() {
		try {
			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(Locators.round)).click();
			Reporter.generateReport(driver, extTest, Status.PASS, "Selected Round Trip");
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL,
					"Round Trip selection failed (may be absent): " + e.getMessage());
		}
	}

	public void enterBoardingPlace(String from) {
		try {

			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(Locators.from)).click();
			driver.findElement(Locators.click_from).sendKeys(from);

			List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
							.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]")));

			if (!results.isEmpty()) {
				results.get(0).click();
			} else {
				driver.findElement(Locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
			}
		} catch (Exception e) {

			Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter origin: " + from);

		}
	}

	public void enterLandingPlace(String to) {
		try {

			//handlePopupIfExists();

			wait.until(ExpectedConditions.elementToBeClickable(Locators.to));
			wait.until(ExpectedConditions.elementToBeClickable(Locators.click_to)).sendKeys(to);

			List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("//span[@class='block truncate' and text()='" + to + "']")));

			if (!results.isEmpty()) {
				results.get(0).click();
			} else {
				driver.findElement(Locators.click_from).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
			}
		} catch (Exception e) {

			Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to enter departure: " + to);

		}
	}
	


	public void setTravellersAndClass(int adults, int children, int infants, String travelClass) {
		try {
			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(Locators.travellersPanel)).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" +adults+ "']"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='" +children+ "']"))).click();

			/*// increase adults (default = 1)
			for (int i = 1; i < adults; i++) {
				wait.until(ExpectedConditions.elementToBeClickable(Locators.adultsPlusBtn)).click();
			}

			for (int i = 0; i < children; i++) {
				wait.until(ExpectedConditions.elementToBeClickable(Locators.childrenPlusBtn)).click();
			}

			for (int i = 0; i < infants; i++) {
				wait.until(ExpectedConditions.elementToBeClickable(Locators.infantsPlusBtn)).click();
			}*/

			// select travel class
			try {
				wait.until(ExpectedConditions.elementToBeClickable(Locators.travelClassDropdown)).click();
				By classOption = Locators.travelClassOption(travelClass);
				wait.until(ExpectedConditions.elementToBeClickable(classOption)).click();
			} catch (Exception ex) {
				Reporter.generateReport(driver, extTest, Status.WARNING,
						"Travel class selection skipped: " + ex.getMessage());
			}

			// apply travellers
			try {
				wait.until(ExpectedConditions.elementToBeClickable(Locators.travellersApplyBtn)).click();
			} catch (Exception ex) {
				Reporter.generateReport(driver, extTest, Status.WARNING,
						"Apply button not clicked: " + ex.getMessage());
			}

			Reporter.generateReport(driver, extTest, Status.PASS,
					"Travellers set: A" + adults + " C" + children + " I" + infants + " Class:" + travelClass);
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to set travellers/class: " + e.getMessage());
		}
	}

	public void clickSearch() {
		try {
			handlePopupIfExists();
			wait.until(ExpectedConditions.elementToBeClickable(Locators.searchButton)).click();
			Reporter.generateReport(driver, extTest, Status.PASS, "Clicked Search");
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to click Search: " + e.getMessage());
		}
	}

	public boolean areResultsDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resultsContainer));
			Reporter.generateReport(driver, extTest, Status.PASS, "Search results displayed");
			return true;
		} catch (Exception e) {
			Reporter.generateReport(driver, extTest, Status.FAIL, "Search results not detected: " + e.getMessage());
			return false;
		}
	}
}
