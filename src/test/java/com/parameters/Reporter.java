package com.parameters;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Reporter {

    public static void generateReport(WebDriver driver, ExtentTest extTest, Status status, String stepMessage) {
        if (status.equals(Status.PASS)) {
            System.out.println(" ******* " + stepMessage + " is passed");
            extTest.log(status, stepMessage);
        } else if (status.equals(Status.FAIL)) {
            System.out.println("***************** step is failed");
            try {
                String screenshotPath = captureScreenshot(driver, stepMessage);
                extTest.log(status, stepMessage,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (IOException e) {
                e.printStackTrace();
                extTest.log(status, stepMessage + " (Screenshot capture failed!)");
            }
        }
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
    	screenshotName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String path = "reports/screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(src, dest);

        // ✅ return the full path (for ExtentReport to attach)
        return dest.getAbsolutePath();
    }
}
