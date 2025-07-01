package com.ei.pwwdseljava.onlineinvoice.tests;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.Assert;
//import org.apache.logging.log4j.core.util.Assert;
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

	@Test(dataProvider = "invoiceData2", enabled = false)
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

	// Test method to validate invoice details for a given invoice ID
	@Test(dataProvider = "invoiceData", enabled = true)
	public void testInvoiceData(int invoiceId) {
		// Fetch the invoice details from the database using the provided invoice ID
		Map<String, String> invoiceDetails = OracleDBUtil.getInvoiceDetailsById(invoiceId);

		// Assert that all required fields are not null or empty
		Assert.assertNotNull(invoiceDetails.get("InvoicePayeeType"),
				"Invoice Payee Type is null for Invoice ID: " + invoiceId);
		Assert.assertNotNull(invoiceDetails.get("InvoiceStatus"),
				"Invoice Status is null for Invoice ID: " + invoiceId);
		Assert.assertNotNull(invoiceDetails.get("ClaimNumber"), "Claim Number is null for Invoice ID: " + invoiceId);
		Assert.assertNotNull(invoiceDetails.get("InvoiceSubmittedDate"),
				"Invoice Submitted Date is null for Invoice ID: " + invoiceId);

		// Example of a more specific assertion (based on expected values, you can
		// modify this)
		System.out.println("Invoice ID: " + invoiceId);
		System.out.println("Invoice Payee Type: " + invoiceDetails.get("InvoicePayeeType"));
		System.out.println("Invoice Status: " + invoiceDetails.get("InvoiceStatus"));
		System.out.println("Claim Number: " + invoiceDetails.get("ClaimNumber"));
		System.out.println("Invoice Submitted Date: " + invoiceDetails.get("InvoiceSubmittedDate"));

		// You can also add specific assertions based on expected values like:
		// Assert.assertEquals(invoiceDetails.get("InvoiceStatus"), "Paid", "noteInvoice
		// status mismatch for Invoice ID: " + invoiceId);
	}

	@DataProvider(name = "invoiceData")
	public Object[][] getInvoiceData() {
		return new Object[][] { { 20454801 } // Test with Invoice ID 101
				// {102}, // Test with Invoice ID 102
				// {103} // Test with Invoice ID 103
		};
	}
}