package com.ixigo.parameters;

import org.testng.Reporter;

/**
 * ReporterUtil - Utility class to log test steps and results
 */
public class ReporterUtil {

    public static void logInfo(String message) {
        System.out.println("[INFO] " + message);  // Console
        Reporter.log("[INFO] " + message);        // TestNG report
    }

    public static void logPass(String message) {
        System.out.println("[PASS] " + message);
        Reporter.log("[PASS] " + message);
    }

    public static void logFail(String message) {
        System.err.println("[FAIL] " + message);
        Reporter.log("[FAIL] " + message);
    }
}
