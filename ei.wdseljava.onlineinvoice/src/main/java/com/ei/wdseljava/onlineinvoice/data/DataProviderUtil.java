package com.ei.wdseljava.onlineinvoice.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ei.wdseljava.onlineinvoice.objects.TestDataXML;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;



public class DataProviderUtil {

    // Method to load login data from config.properties file
    public static Object[][] loadLoginDataFromConfig() {
        Properties properties = new Properties();
        String url = null;
        String username = null;
        String password = null;

        try {
            // Load the properties file
            FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties"); // Update the path as needed
            properties.load(inputStream);

            // Fetch username and password from config.properties
            url = properties.getProperty("login.url");
            username = properties.getProperty("login.username");
            password = properties.getProperty("login.password");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the login data as an Object array for DataProvider
        return new Object[][] {
            { url, username, password }
        };
    }
    
    
    public static TestDataXML parseXML(String filePath) throws IOException {
            XmlMapper xmlMapper = new XmlMapper();
            TestDataXML testData = xmlMapper.readValue(new File(filePath), TestDataXML.class);

            // Dynamically set the datafields based on the number of datafields in the XML
            File xmlFile = new File(filePath);
            xmlMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

            // Return populated TestDataXML object
            return testData;
        }
    
    
    
}




