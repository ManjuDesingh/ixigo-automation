package com.ixigo.pages;

import com.ixigo.objectRepository.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

/**
 * FiltersPage - handles all filter options on search results page
 */
public class FiltersPage extends BasePage {

    private WebDriver driver;

    // ---------- Locators ----------
    @FindBy(xpath = "//div[@class='filter-section']//label")
    private List<WebElement> filterOptions;

    @FindBy(id = "applyFiltersBtn")
    private WebElement applyFiltersButton;

    @FindBy(id = "resetFiltersBtn")
    private WebElement resetFiltersButton;

    // ---------- Constructor ----------
    public FiltersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Actions ----------
    public void selectFilter(String filterName) {
        for (WebElement filter : filterOptions) {
            if (filter.getText().equalsIgnoreCase(filterName)) {
                filter.click();
                break;
            }
        }
    }

    public void clickApplyFilters() {
        applyFiltersButton.click();
    }

    public void clickResetFilters() {
        resetFiltersButton.click();
    }

    public boolean isFilterApplied(String filterName) {
        for (WebElement filter : filterOptions) {
            if (filter.getText().equalsIgnoreCase(filterName)) {
                return filter.isSelected();
            }
        }
        return false;
    }
}
