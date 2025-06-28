package com.ei.pwwdseljava.onlineinvoice.tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ei.pwwdseljava.onlineinvoice.config.BaseClass;
import com.ei.pwwdseljava.onlineinvoice.config.ConfigReader;
import com.ei.pwwdseljava.onlineinvoice.pages.CbInvoiceEntryPage;
import com.ei.pwwdseljava.onlineinvoice.pages.CbLoginPage;

public class SubmitInvoiceTestCBH extends BaseClass {

	@Test
	@Parameters({ "runner" })
	public void testLoginAndInvoiceEntry(String runner) {
		System.out.println("Running test with runner = " + runner);
		System.out.println("▶️ Page available? " + (page != null));

		// Navigate to login page using Playwright
		page.navigate(ConfigReader.get("login.url"));

		// Perform login
		CbLoginPage myloginPage = new CbLoginPage(page);
		myloginPage.login(ConfigReader.get("login.username"), ConfigReader.get("login.password"));
		System.out.println("✅ Login successful");

		// Interact with invoice entry page
		CbInvoiceEntryPage invoicePage = new CbInvoiceEntryPage(page);
		invoicePage.selectPractice("PURE SKIN CLINIC PTY LTD");

		System.out.println("✅ Practice selection done");
	}

	@Test
	public void testSubmitInvoice() {
		try {
			// ...existing test logic...
		} catch (Exception e) {
			// ExtentManager.captureScreenshot(driver, test); // Capture screenshot on
			// failure
			throw e;
		}
	}
}