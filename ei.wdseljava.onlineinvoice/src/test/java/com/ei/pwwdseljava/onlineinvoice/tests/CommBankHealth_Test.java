package com.ei.pwwdseljava.onlineinvoice.tests;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.ei.pwwdseljava.onlineinvoice.config.BaseClass;
import com.ei.pwwdseljava.onlineinvoice.config.ConfigReader;
import com.ei.pwwdseljava.onlineinvoice.data.DataProviderUtil;
import com.ei.pwwdseljava.onlineinvoice.pages.CbInvoiceEntry;
import com.ei.pwwdseljava.onlineinvoice.pages.CbLogin;
import com.ei.pwwdseljava.onlineinvoice.pages.CbQuoteInvoiceSubmit;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;
import com.ei.pwwdseljava.onlineinvoice.utils.OracleDBUtil;

public class CommBankHealth_Test extends BaseClass {

	// DataProvider to get login data from config file
	@DataProvider(name = "loginData")
	public Object[][] getLoginData() {
		// Fetch data using ConfigReader
		String url = ConfigReader.get("login.url");
		String username = ConfigReader.get("login.username");
		String password = ConfigReader.get("login.password");

		return new Object[][] { { url, username, password } };
	}

	@DataProvider(name = "invoiceData")
	public Object[][] getInvoiceData(ITestContext context) {
		// Fetch all data from the XML file
		Object[][] allData = DataProviderUtil.getInvoiceDataFromXML("src/test/resources/test-data/invoice-data.xml");

		// Retrieve the 'entryIndex' parameter from testng.xml
		String entryIndex = context.getCurrentXmlTest().getParameter("entryIndex");

		if (entryIndex == null || entryIndex.equalsIgnoreCase("all")) {
			// Return only the first entry to ensure single execution
			return new Object[][] { allData[0] };
		} else {
			try {
				// Parse the entry index and return the specific entry
				int index = Integer.parseInt(entryIndex);
				return new Object[][] { allData[index] };
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid entryIndex parameter: " + entryIndex, e);
			}
		}
	}

	private CbInvoiceEntry cbInvoiceEntry;
	private CbQuoteInvoiceSubmit cbQuoteAndInvoice;
	private String generatedInvoiceNumber; // Store invoice number to pass between test methods

	private static final Logger logger = LoggerFactory.getLogger(CommBankHealth_Test.class);

	@BeforeClass
	public void setUp() {
		super.setUp("chrome", "selenium"); // Pass required arguments
		extent = ExtentManager.getReporterInstance();
		cbInvoiceEntry = new CbInvoiceEntry(driver, extent.createTest("Invoice Entry Setup"));
	}

	@Test(dataProvider = "loginData")
	public void loginTest(String url, String username, String password) {
		ExtentTest test = extent.createTest("loginTest");
		try {
			logger.info("Starting loginTest with URL: {}, Username: {}", url, username);

			CbLogin cbLogin = new CbLogin(driver, test);
			cbLogin.login(url, username, password);
			System.out.println("-->Login successful with username: " + username);

		} catch (Exception e) {
			logger.error("Error in loginTest: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("loginTest failed: " + e.getMessage());
			throw new RuntimeException("Error during loginTest", e);
		}
	}

	@Test(dataProvider = "invoiceData", dependsOnMethods = { "loginTest" })
	public void invoiceEntryTest(String practice, String practitioner, String patient, String item)
			throws InterruptedException {
		ExtentTest test = extent.createTest("invoiceEntryTest");
		try {
			logger.info("Starting invoiceEntryTest with Practice: {}, Practitioner: {}, Patient: {}, Item: {}",
					practice, practitioner, patient, item);
			System.out.println("-->Starting invoice entry test with Practice: " + practice + ", Practitioner: "
					+ practitioner + ", Patient: " + patient + ", Item: " + item);

			cbInvoiceEntry.selectPractice(practice, practitioner);
			cbInvoiceEntry.selectPatient(patient);
			cbInvoiceEntry.selectItem(item);

			logger.info("Invoice entry test completed successfully.");
			test.pass("Invoice entry test completed successfully.");
		} catch (Exception e) {
			logger.error("Error in invoiceEntryTest: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("invoiceEntryTest failed: " + e.getMessage());
			System.out.println("-->Error in invoiceEntryTest: " + e.getMessage());
			throw new RuntimeException("Error during invoiceEntryTest", e);
		}
	}

	@Test(dependsOnMethods = { "invoiceEntryTest" })
	public void quoteInvoiceSubmitTest() throws Exception {
		ExtentTest test = extent.createTest("quoteInvoiceSubmitTest");
		try {
			logger.info("Starting quoteInvoiceSubmitTest.");

			cbQuoteAndInvoice = new CbQuoteInvoiceSubmit(driver, test);
			Map<String, String> resultData = cbQuoteAndInvoice.quoteInvoiceSubmission();
			String claimNumber = resultData.get("claimNumber");
			String invoiceNumber = resultData.get("invoiceNumber");

			// Store the invoice number for use in subsequent test methods
			this.generatedInvoiceNumber = invoiceNumber;

			logger.info("Claim Number: {}, Invoice Number: {}", claimNumber, invoiceNumber);
			test.pass("Claim Number: " + claimNumber + ", Invoice Number: " + invoiceNumber);

			// Save the data as a JSON file
			DataProviderUtil.saveInvoiceDataAsJson(invoiceNumber, claimNumber);

			test.pass("quoteInvoiceSubmitTest completed successfully.");
		} catch (Exception e) {
			logger.error("Error in quoteInvoiceSubmitTest: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("quoteInvoiceSubmitTest failed: " + e.getMessage());
			throw new RuntimeException("Error during quoteInvoiceSubmitTest", e);
		}
	}

	// Test method to validate that Invocie is created in Tempus database
	@Test(dependsOnMethods = { "quoteInvoiceSubmitTest" }, enabled = true)
	public void testInvoiceData() {
		ExtentTest test = extent.createTest("testInvoiceData");
		try {
			// Use the invoice number generated from quoteInvoiceSubmitTest
			Thread.sleep(100000); // Ensure the invoice is created before fetching data
			String invoiceNumber = this.generatedInvoiceNumber;

			logger.info("Starting testInvoiceData with Invoice Number: {}", invoiceNumber);

			int invoiceId = Integer.parseInt(invoiceNumber);

			// Fetch the invoice details from the database
			Map<String, String> invoiceDetails = OracleDBUtil.getInvoiceDetailsById(invoiceId);
			Thread.sleep(2000);
			logger.info("Fetched invoice details for Invoice Number: {}", invoiceNumber);

			// Check if invoice details are empty - Invoice creation failed
			if (invoiceDetails.isEmpty()) {
				String errorMessage = "Invoice data not found in Tempus database, Invoice creation failed for Invoice Number: "
						+ invoiceNumber;
				logger.error(errorMessage);
				System.out.println("-->Error: " + errorMessage);
				ExtentManager.captureScreenshot(driver, test);
				test.fail(errorMessage);
				throw new RuntimeException(errorMessage);
			}

			/*
			 * // Assert that all required fields are not null or empty
			 * Assert.assertNotNull(invoiceDetails.get("InvoicePayeeType"),
			 * "Invoice Payee Type is null for Invoice Number: " + invoiceNumber);
			 * Assert.assertNotNull(invoiceDetails.get("InvoiceStatus"),
			 * "Invoice Status is null for Invoice Number: " + invoiceNumber);
			 * Assert.assertNotNull(invoiceDetails.get("ClaimNumber"),
			 * "Claim Number is null for Invoice Number: " + invoiceNumber);
			 * Assert.assertNotNull(invoiceDetails.get("InvoiceSubmittedDate"),
			 * "Invoice Submitted Date is null for Invoice Number: " + invoiceNumber);
			 */

			// Log the retrieved values
			logger.info("Invoice validation completed successfully for Invoice Number: {}", invoiceNumber);
			System.out.println("Invoice Number: " + invoiceNumber);
			System.out.println("Invoice Payee Type: " + invoiceDetails.get("InvoicePayeeType"));
			System.out.println("Invoice Status: " + invoiceDetails.get("InvoiceStatus"));
			System.out.println("Claim Number: " + invoiceDetails.get("ClaimNumber"));
			System.out.println("Invoice Submitted Date: " + invoiceDetails.get("InvoiceSubmittedDate"));

			test.pass("Invoice created succesfully in Tempus with Invoice Number: " + invoiceNumber + "and Status: "
					+ invoiceDetails.get("InvoiceStatus"));

		} catch (NumberFormatException e) {
			logger.error("Invalid invoice number format: {}", this.generatedInvoiceNumber, e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Invalid invoice number format: " + this.generatedInvoiceNumber);
			throw new RuntimeException("Invalid invoice number format: " + this.generatedInvoiceNumber, e);
		} catch (Exception e) {
			logger.error("Error in testInvoiceData: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("testInvoiceData failed: " + e.getMessage());
			throw new RuntimeException("Error during testInvoiceData", e);
		}
	}

}