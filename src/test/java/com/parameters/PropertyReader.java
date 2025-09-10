package com.parameters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	public static Properties readProperties() {

		String filename = "src\\test\\resources\\Config\\config.properties";
		Properties prop = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("File Name is not correct,please check the file name");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
