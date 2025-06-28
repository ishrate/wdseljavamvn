package com.ei.pwwdseljava.onlineinvoice.tests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ei.pwwdseljava.onlineinvoice.config.BaseClass;
import com.ei.pwwdseljava.onlineinvoice.config.ConfigProperties;
import com.ei.pwwdseljava.onlineinvoice.data.DataProviderUtil;
import com.ei.pwwdseljava.onlineinvoice.models.TestDataXML;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;

public class GoogleSearchTest extends BaseClass {

	private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);

	@Test
	public void testGoogleSearch() {

		ExtentTest test = ExtentManager.getReporterInstance().createTest("Google Search Test",
				"Searches 'Cheese' in Google");

		try {
			// Get URL from config.properties
			String url = ConfigProperties.getProperty("url");

			// Parse search term from test-data.xml
			String filePath = "src/test/resources/test-data/search-query.xml";
			TestDataXML testData = DataProviderUtil.parseXML(filePath);
			String searchTerm = testData.getDatafield1();

			// Validate URL
			if (url == null || url.isEmpty()) {
				throw new IllegalArgumentException("URL is missing or invalid in config.properties");
			}

			// Validate search term
			if (searchTerm == null || searchTerm.isEmpty()) {
				throw new IllegalArgumentException("Search term is missing or invalid in search-query.xml");
			}

			// Use the WebDriver from BaseTest (driver is already initialized in BaseTest)
			// driver.get(url);

			test.log(Status.INFO, "Navigating to Google");
			// Log before navigating
			System.out.println("Navigating to Google...");

			System.out.println("we are searching " + searchTerm);
			driver.get(url);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			// Assert if the page title contains the search term
			assertTrue(driver.getTitle().contains(searchTerm),
					"Search term not found in the page title: " + searchTerm);

			// Find the search input element and search for the term from XML
			WebElement searchBox = driver.findElement(By.name("q"));
			searchBox.sendKeys(searchTerm);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			System.out.println("we are about to submit  " + searchTerm);
			searchBox.submit();

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

			// Assert if the page title contains the search term

			System.out.println("now submitted  " + searchTerm);

			test.log(Status.INFO, "Verifying page title contains 'Chess'");
			// assert driver.getTitle().contains("Cheese");

			// Validate if the search term is in the page title
			if (driver.getTitle().contains("Cheese")) {
				logger.info("test pass");
				System.out.println("Test Passed--Search term used: " + searchTerm);
			}

			// print the results for debugging
			System.out.println("Search term used: " + searchTerm);
			System.out.println("Page title: " + driver.getTitle());

		} catch (Exception e) {
			e.printStackTrace();
			// logger.debug(null, e);
			test.log(Status.FAIL, "Test failed due to: " + e.getMessage());
			ExtentManager.captureScreenshot(driver, test);
		} // finally {
			// Add custom info to report (e.g., system info)
			// ExtentReportUtils.addCustomInfo(test);
			// }
	}
}