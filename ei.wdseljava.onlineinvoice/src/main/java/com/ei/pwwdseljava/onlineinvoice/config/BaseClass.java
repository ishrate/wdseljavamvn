//Author: Eamon Ishrat, Automation Architect
//############################################/

package com.ei.pwwdseljava.onlineinvoice.config;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ei.pwwdseljava.onlineinvoice.factory.PlaywrightFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	protected WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);
	// protected ExtentTest test;
	protected Playwright playwright;
	protected Browser browser;
	protected BrowserContext context;
	protected Page page;

	protected static ExtentReports extent;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "browser", "runner" })
	public void setUp(@Optional("chrome") String browserName, @Optional("selenium") String runner) {
		try {
			// Initialize ExtentReports
			if (extent == null) {
				ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
						"test-output/ExtentReports/extent-report.html");
				extent = new ExtentReports();
				extent.attachReporter(sparkReporter);

				sparkReporter.config().setDocumentTitle("Selenium Extent Report");
				sparkReporter.config().setReportName("Functional Test Report");
				sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
			}

			logger.info("🚀 Test setup started. Runner: {} | Browser: {}", runner, browserName);

			if (runner.equalsIgnoreCase("selenium")) {

				// Set up ChromeDriver using WebDriverManager
				System.setProperty("oracle.net.tns_admin", "C:\\3PP\\Wallet_AUTSELWEBJAVEXT");
				WebDriverManager.chromedriver().driverVersion("137.0.7151.69").setup();
				System.out.println(
						"WebDriverManager ChromeDriver Path: " + System.getProperty("webdriver.chrome.driver"));

				// Add --detach option to keep the browser open
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--detach");

				// Initialize ChromeDriver with options
				driver = new ChromeDriver(options); // Now WebDriverManager handles the driver setup

				// Log info-level message
				logger.info("ChromeDriver setup completed and browser launched.");
				System.out.println("ChromeDriver setup completed and browser launched.");

				// Optional: Maximize the browser window for better visibility
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			} else if (runner.equalsIgnoreCase("playwright")) {
				System.setProperty("PLAYWRIGHT_DISABLE_USAGE_REPORTING", "1");
				System.setProperty("PLAYWRIGHT_SKIP_VALIDATE_HOST_REQUIREMENTS", "true");
				PlaywrightFactory.initBrowser(browserName);
				playwright = PlaywrightFactory.getPlaywright();
				browser = PlaywrightFactory.getBrowser();
				context = PlaywrightFactory.getContext();
				page = PlaywrightFactory.getPage();

				if (page == null) {
					logger.error("Page is null after initialization! Check PlaywrightFactory.initBrowser logic.");
					throw new RuntimeException("Page is null after initialization!");
				} else {
					logger.info("Page initialized successfully.");
				}

				logger.info("Navigated to: ", ConfigReader.get("login.url"));
			} else {
				throw new IllegalArgumentException("Unsupported test runner: " + runner);
			}
		} catch (Exception e) {
			logger.error("Error during setup: {}", e.getMessage(), e);
			throw e;
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		try {
			logger.info("Cleaning up test execution...");

			if (driver != null) {
				// Commenting out driver.quit() to keep the browser open
				// driver.quit();
			}

			boolean keepOpen = ConfigReader.getBoolean("playwright.keepopen");
			logger.info("Config value 'playwright.keepopen': ", keepOpen);

			if (!keepOpen) {
				if (page != null) {
					page.close();
				}
				if (context != null) {
					context.close();
				}
				if (browser != null) {
					// browser.close();
				}
				if (playwright != null) {
					// playwright.close();
				}
			}
		} catch (Exception e) {
			logger.error("Error during teardown: {}", e.getMessage(), e);
		} finally {
			if (extent != null) {
				extent.flush();
			}
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public Page getPage() {
		return page;
	}

}