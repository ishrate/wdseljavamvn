package com.ei.pwwdseljava.onlineinvoice.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ei.pwwdseljava.onlineinvoice.utils.OracleDBUtil;

public class OracleDataTest {

	@Test(dataProvider = "oracleData", enabled = false)
	public void testDatabaseData(String query, String expectedValue) {
		String result = OracleDBUtil.executeQuery(query).get(0);
		assertTrue(result.contains(expectedValue),
				"Data from DB: " + result + " does not contain expected value: " + expectedValue);
	}

	@DataProvider(name = "oracleData")
	public Object[][] getOracleData() {
		return new Object[][] { { "SELECT * FROM your_table WHERE condition = 'value'", "expected_value_1" },
				{ "SELECT * FROM another_table WHERE condition = 'another_value'", "expected_value_2" } };
	}

	@Test(dataProvider = "tempusData", enabled = false)
	public void testTempusData(String query, String expectedValue) {
		String result = OracleDBUtil.executeQuery(query).get(0);
		// assertTrue(result.contains(expectedValue), "Data from DB: " + result + " does
		// not contain expected value: " + expectedValue);
	}

	@DataProvider(name = "tempusData")
	public Object[][] getTempusData() {
		return new Object[][] { { "SELECT * FROM your_table WHERE condition = 'value'", "expected_value_1" },
				{ "SELECT * FROM another_table WHERE condition = 'another_value'", "expected_value_2" } };
	}

	@Test(dataProvider = "invoiceData", enabled = true)
	public void testDatabaseData(int invoiceId) {
		// Get the Invoice data (Status and Amount) for the given Invoice ID
		System.out.println("Searching Invoice with ID: " + invoiceId);
		var result = OracleDBUtil.getInvoiceDataById(invoiceId);

		// Print the results
		if (!result.isEmpty()) {
			System.out.println("NOW IF THAT IS PRINTED THAT WOULD BE MEGA");
			result.forEach(System.out::println); // Prints each result item
		}

		// Add assertion for testing (optional, depending on your needs)
		// assertTrue(result.size() > 0, "No invoice data was retrieved for Invoice ID:
		// " + invoiceId);
	}

	@DataProvider(name = "invoiceData")
	public Object[][] getInvoiceData() {
		return new Object[][] { { 20454801 } // Test with Invoice ID 101
				// {102}, // Test with Invoice ID 102
				// {103} // Test with Invoice ID 103
		};
	}

	@Test(dataProvider = "oracleData", enabled = false)
	public void testOracleData() {
		try {
			// ...existing test logic...
		} catch (Exception e) {
			// ExtentManager.captureScreenshot(driver, test); // Capture screenshot on
			// failure
			throw e;
		}
	}
}