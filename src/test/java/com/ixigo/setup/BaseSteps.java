package com.ixigo.setup;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ixigo.parameters.PropertyReader;

public class BaseSteps {

    // Use ThreadLocal for parallel execution support
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties prop = PropertyReader.readProperties();
    public static final int EXPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("ExplicitWait", "10"));
    public static final int IMPLICIT_WAIT_TIME = Integer.parseInt(prop.getProperty("ImplicitWait", "10"));
    public static final int PAGE_LOAD_TIME = Integer.parseInt(prop.getProperty("PageLoadTime", "30"));

    public void launchBrowser() {
        String browserName = prop.getProperty("Browser", "Chrome").trim();
        String url = prop.getProperty("URL", "https://www.ixigo.com").trim();

        try {
            switch (browserName.toUpperCase()) {
                case "CHROME":
                    driver.set(setupChromeDriver());
                    break;
                case "FIREFOX":
                    driver.set(new FirefoxDriver());
                    break;
                case "EDGE":
                    driver.set(new EdgeDriver());
                    break;
                case "SAFARI":
                    driver.set(new SafariDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            WebDriver webDriver = getDriver();
            webDriver.manage().window().maximize();
            // Configure timeouts
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME));
            
            webDriver.get(url);

        } catch (Exception e) {
            System.err.println("Failed to launch browser: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Browser launch failed", e);
        }
    }

    private ChromeDriver setupChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.password_manager_leak_detection", false);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        // Add arguments for headless mode, if configured
        if (Boolean.parseBoolean(prop.getProperty("Headless", "false"))) {
            chromeOptions.addArguments("--headless=new");
        }
        // Add other common arguments for stability
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920,1080");

        return new ChromeDriver(chromeOptions);
    }

    // Public method to get the driver instance
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Public method to get a WebDriverWait instance
    public static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(EXPLICIT_WAIT_TIME));
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Clean up the ThreadLocal variable
        }
    }

    // Avoid using static sleep in test logic. Use explicit waits instead.
    @Deprecated
    public static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Sleep was interrupted: " + e.getMessage());
        }
    }
}