package com.ixigo.parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static Properties prop = new Properties();

    static {
        try {
            String filePath = System.getProperty("user.dir") + "/src/test/resources/Config/config.properties";
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get property by key
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    // Get property by key with default value
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    // Get the Properties object
    public static Properties readProperties() {
        return prop;
    }
}