package com.ixigo.parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    // Method to read the config.properties file
    public static Properties readProperties() {
        Properties prop = new Properties();
        try {
            // Load the properties file from the config directory
            String filePath = System.getProperty("user.dir") + "/src/test/resources/Config/config.properties";
            FileInputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            e.printStackTrace();
        }
        return prop;
    }

    // Optional: Method to get a specific property value
    public static String getProperty(String key) {
        return readProperties().getProperty(key);
    }
}