//Author: Eamon Ishrat, Automation Architect
//************************************************

package com.ei.pwwdseljava.onlineinvoice.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new IllegalArgumentException("Sorry, unable to find config.properties");
			}
			properties.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error loading config.properties", ex); // Rethrow the exception to fail fast
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}