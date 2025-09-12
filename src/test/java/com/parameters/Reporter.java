package com.parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Reporter {

    // root reports folder (adjust if you have a reports path elsewhere)
    private static final String REPORTS_ROOT = System.getProperty("user.dir") + File.separator + "test-output";
    private static final String SCREENSHOT_DIR = REPORTS_ROOT + File.separator + "screenshots";

    public static void generateReport(WebDriver driver, ExtentTest extTest, Status status, String message) {
        // Log the message to extent
        if (extTest != null) {
            extTest.log(status, message);
        }

        // Capture screenshot only for FAIL (or whatever granularity you prefer)
        try {
            if (status == Status.FAIL && driver != null) {
                String path = captureScreenshotSafe(driver);
                if (path != null && extTest != null) {
                    // attach screenshot path textually (many Extent setups accept adding screen capture)
                    try {
                        extTest.addScreenCaptureFromPath(path);
                    } catch (Exception e) {
                        // attaching can fail for various reasons — log a warning in extent
                        extTest.info("Screenshot captured but failed to attach: " + e.getMessage());
                    }
                } else {
                    if (extTest != null) extTest.info("Screenshot not available (capture failed).");
                }
            }
        } catch (Exception e) {
            // never throw from reporter — only log
            if (extTest != null) extTest.warning("Reporter.generateReport encountered an error: " + e.getMessage());
        }
    }

    /**
     * Capture screenshot safely and return absolute path to saved file, or null on failure.
     * This method will not throw checked IOExceptions to callers.
     */
    public static String captureScreenshotSafe(WebDriver driver) {
        try {
            // Ensure dirs exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Build a safe filename: use timestamp without colon and only safe chars
            String timestamp = java.time.LocalDateTime.now()
                    .toString()                       // e.g. 2025-09-10T10:40:13.123
                    .replace(":", "-")                // remove colon characters (illegal on Windows filenames)
                    .replace("T", "_")
                    .replace(".", "-");

            String filename = "screenshot_" + timestamp + ".png";
            Path dest = screenshotDir.resolve(filename);

            // Take screenshot to a temporary file first
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Use java.nio copy for robustness
            Files.copy(srcFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

            return dest.toAbsolutePath().toString();
        } catch (IOException ioe) {
            // IO error (path invalid, permission, etc.). Log to console and return null.
            System.err.println("Reporter.captureScreenshotSafe IOException: " + ioe.getMessage());
            return null;
        } catch (WebDriverException wde) {
            // WebDriver failed to capture screenshot
            System.err.println("Reporter.captureScreenshotSafe WebDriverException: " + wde.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Reporter.captureScreenshotSafe unexpected error: " + ex.getMessage());
            return null;
        }
    }
}