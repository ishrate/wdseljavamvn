package com.ei.pwwdseljava.onlineinvoice.pages;

import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.CHECK_ELIGIBILITY_BUTTON_XPATH;
import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.CLAIM_NUMBER_XPATH;
import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.CLAIM_RESULT_SPAN_XPATH;
import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.INVOICE_NUMBER_XPATH;
import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.INVOICE_RESULT_ELEMENTS_XPATH;
import static com.ei.pwwdseljava.onlineinvoice.objects.QuoteInvoiceSubmitObjects.PROCESS_INVOICE_BUTTON_XPATH;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.ei.pwwdseljava.onlineinvoice.config.BaseClass;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;

public class CbQuoteInvoiceSubmit extends BaseClass {

	private WebDriver driver;
	private WebDriverWait wait;
	private ExtentTest test;

	private static final Logger logger = LoggerFactory.getLogger(CbQuoteInvoiceSubmit.class);

	public CbQuoteInvoiceSubmit(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.test = test;
	}

	public Map<String, String> quoteInvoiceSubmission() throws InterruptedException {

		Map<String, String> resultData = new HashMap<>();
		try {
			System.out.println("-->Driver available? " + (driver != null));
			test.info("Starting quoteInvoiceSubmission.");
			logger.info("Starting quoteInvoiceSubmission.");
			System.out.println("-->Starting quoteInvoiceSubmission.");

			// Check Quote Eligibility
			sleep(2000);
			test.info("Clicking 'Check Eligibility' button.");
			WebElement checkEligibilityBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(CHECK_ELIGIBILITY_BUTTON_XPATH)));
			checkEligibilityBtn.click();
			test.pass("'Check Eligibility' button clicked.");
			System.out.println("-->Check Eligibility button clicked.");

			sleep(8000);

			By claimResultSpan = By.xpath(CLAIM_RESULT_SPAN_XPATH);
			List<WebElement> claimResultElements = driver.findElements(claimResultSpan);

			if (!claimResultElements.isEmpty()) {
				String result = claimResultElements.get(0).getText();
				test.pass("Quote Eligibility Success, Claim result returned: " + result);
				logger.info("Quote Eligibility Success, Claim result returned: {}", result);
				System.out.println("-->Quote Eligibility Success, Claim result returned: " + result);
			} else {
				test.warning("Quote Eligibility Failed, Claim result not returned. Continuing to Invoice Submission.");
				logger.warn("Quote Eligibility Failed, Claim result not returned. Continuing to Invoice Submission.");
				System.out.println(
						"-->Quote Eligibility Failed, Claim result not returned. Continuing to Invoice Submission.");
			}

			// Submitting Invoice
			test.info("Clicking 'Process Invoice' button.");
			WebElement processInvoiceBtn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(PROCESS_INVOICE_BUTTON_XPATH)));
			processInvoiceBtn.click();
			test.pass("'Process Invoice' button clicked.");
			System.out.println("-->Process Invoice button clicked.");

			sleep(8000);

			List<WebElement> invoiceResultElements = driver.findElements(By.xpath(INVOICE_RESULT_ELEMENTS_XPATH));

			if (!invoiceResultElements.isEmpty()) {
				test.pass("Invoice submitted successfully.");
				logger.info("Invoice submitted successfully.");
				System.out.println("-->Invoice submitted successfully.");

				String invoiceNo = driver.findElement(By.xpath(INVOICE_NUMBER_XPATH)).getText();
				String claimNumber = driver.findElement(By.xpath(CLAIM_NUMBER_XPATH)).getText();

				test.pass("Invoice No: " + invoiceNo + ", Claim Number: " + claimNumber);
				logger.info("Invoice No: {}, Claim Number: {}", invoiceNo, claimNumber);
				System.out.println("-->Success: Invoice No: " + invoiceNo + ", Claim Number: " + claimNumber);

				// Store the invoice number and claim number in resultData
				resultData.put("invoiceNumber", invoiceNo);
				resultData.put("claimNumber", claimNumber);
			} else {
				test.fail("Invoice submission failed.");
				logger.error("Invoice submission failed.");
				System.out.println("-->Error: Invoice submission failed.");
			}
		} catch (Exception e) {
			logger.error("Error in quoteInvoiceSubmission: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Error in quoteInvoiceSubmission: " + e.getMessage());
			System.out.println("-->Error in quoteInvoiceSubmission: " + e.getMessage());
			throw e;
		}
		return resultData;
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}