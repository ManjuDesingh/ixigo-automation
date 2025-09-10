package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.objectrepository.Locators;
import com.parameters.Reporter;



	public class SelectionPage extends Searchingpage {

	    public SelectionPage(WebDriver driver, ExtentTest extTest) {
	        super(driver, extTest);   // call Searchingpage constructor
	    }

	    public boolean loadPage() {
	    	openFlightsTab();
	    	selectRoundTrip();
	    	enterBoardingPlace("chennai");
	    	enterLandingPlace("Mumbai");
	    	setTravellersAndClass(1,0,1,"Economy");
	    	clickSearch() ;
	    	
	        try {
	            wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.resultsContainer));
	            Reporter.generateReport(driver, extTest, Status.PASS, "The page has been landed");
	            return true;
	        } catch (Exception e) {
	            Reporter.generateReport(driver, extTest, Status.FAIL, "Failed to load page");
	            return false;
	        }
	    }
	    
	    public boolean firstfilter(String filter) {
	    	
	    	
			return false;    	
	    }
	}


