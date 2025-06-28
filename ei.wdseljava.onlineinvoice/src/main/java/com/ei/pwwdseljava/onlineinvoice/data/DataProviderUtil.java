//Author: Eamon Ishrat, Automation Architect
//************************************************

package com.ei.pwwdseljava.onlineinvoice.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.ei.pwwdseljava.onlineinvoice.models.TestDataXML;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DataProviderUtil {

	// Method to load login data from config.properties file
	public static Object[][] loadLoginDataFromConfig() {
		Properties properties = new Properties();
		String url = null;
		String username = null;
		String password = null;

		try {
			// Load the properties file
			FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties"); // Update the
																										// path as
																										// needed
			properties.load(inputStream);

			// Fetch username and password from config.properties
			url = properties.getProperty("login.url");
			username = properties.getProperty("login.username");
			password = properties.getProperty("login.password");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Return the login data as an Object array for DataProvider
		return new Object[][] { { url, username, password } };
	}

	public static TestDataXML parseXML(String filePath) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		TestDataXML testData = xmlMapper.readValue(new File(filePath), TestDataXML.class);

		// Dynamically set the datafields based on the number of datafields in the XML
		File xmlFile = new File(filePath);
		xmlMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
				true);

		// Return populated TestDataXML object
		return testData;
	}

	public static Object[][] getInvoiceDataFromXML(String filePath) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList entries = doc.getElementsByTagName("entry");
			Object[][] data = new Object[entries.getLength()][4];

			for (int i = 0; i < entries.getLength(); i++) {
				data[i][0] = doc.getElementsByTagName("practice").item(i).getTextContent();
				data[i][1] = doc.getElementsByTagName("practitioner").item(i).getTextContent();
				data[i][2] = doc.getElementsByTagName("patient").item(i).getTextContent();
				data[i][3] = doc.getElementsByTagName("item").item(i).getTextContent();
			}

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[0][0];
		}
	}

	public static void saveInvoiceDataAsJson(String invoiceNumber, String claimNumber) {
		try {
			// Create a map to hold the data
			Map<String, String> data = new HashMap<>();
			data.put("invoiceNumber", invoiceNumber);
			data.put("claimNumber", claimNumber);

			// Generate a timestamped filename
			String fileName = "invoice_data_" + System.currentTimeMillis() + ".json";

			// Create a directory for storing invoice data files
			String directoryPath = "test-output/invoice-data";
			Files.createDirectories(Paths.get(directoryPath));

			// Save the JSON file to the directory
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(Paths.get(directoryPath + "/" + fileName).toFile(), data);

			System.out.println("JSON file saved: " + directoryPath + "/" + fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to save JSON file", e);
		}
	}
}