package com.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.parameters.PropertyReader;

public class Base {

    public static WebDriver driver;

    // ✅ Launch browser with default fallback
    public static WebDriver launchBrowser() {
        Properties prop = PropertyReader.readProperties();

        // default to Chrome if property is missing
        String browser = prop.getProperty("Browser", "Chrome"); 
        String url = prop.getProperty("URL", "https://www.ixigo.com");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println("❌ Browser not supported: " + browser + ". Defaulting to Chrome.");
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.get(url);

        return driver;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void quitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
