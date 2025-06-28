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
			throw e;
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

			// CbInvoiceEntry cbInvoiceEntry = new CbInvoiceEntry(driver, test);
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
			throw e;
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

			logger.info("Claim Number: {}, Invoice Number: {}", claimNumber, invoiceNumber);
			test.pass("Claim Number: " + claimNumber + ", Invoice Number: " + invoiceNumber);

			// Save the data as a JSON file
			DataProviderUtil.saveInvoiceDataAsJson(invoiceNumber, claimNumber);

			test.pass("quoteInvoiceSubmitTest completed successfully.");
		} catch (Exception e) {
			logger.error("Error in quoteInvoiceSubmitTest: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("quoteInvoiceSubmitTest failed: " + e.getMessage());
			throw e;
		}
	}

}