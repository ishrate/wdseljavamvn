package com.ei.wdseljava.onlineinvoice.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ei.wdseljava.onlineinvoice.utils.ExtentManager;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
   // protected ExtentTest test;

    @BeforeTest
    public void setup() {
        // Set up ChromeDriver using WebDriverManager
        //WebDriverManager.chromedriver().setup();  // WebDriverManager takes care of downloading ChromeDriver
        //WebDriverManager.chromedriver().clearResolutionCache();
    	System.setProperty("oracle.net.tns_admin", "C:\\3PP\\Wallet_AUTSELWEBJAVEXT");
        WebDriverManager.chromedriver().driverVersion("137.0.7151.69").setup(); 
        System.out.println("WebDriverManager ChromeDriver Path: " + System.getProperty("webdriver.chrome.driver"));
        // Initialize ChromeDriver
        driver = new ChromeDriver();  // Now WebDriverManager handles the driver setup
        
     // Log info-level message
        logger.info("ChromeDriver setup completed and browser launched.");
        System.out.println("ChromeDriver setup completed and browser launched.");

        // Optional: Maximize the browser window for better visibility
        driver.manage().window().maximize();
        //ExtentManager.getReporterInstance();
    }
    
	/*
	 * @BeforeMethod public void beforeMethod(org.testng.ITestContext context) { //
	 * This method will run before each test method
	 * 
	 * // Fetch the current method name dynamically using TestNG's ITestContext
	 * String testName = context.getCurrentXmlTest().getName();
	 * 
	 * // Create a new ExtentTest instance for each test method test =
	 * ExtentManager.getReporterInstance().createTest(testName,
	 * "Description of the test method"); }
	 */
    
    
	/*
	 * @AfterMethod public void afterMethod(org.testng.ITestResult result) { //
	 * Optionally, you can attach information or screenshots to the report here if
	 * (result.getStatus() == org.testng.ITestResult.FAILURE) { // Capture a
	 * screenshot or log additional info on failure
	 * ExtentManager.captureScreenshot(driver, test); }
	 * 
	 * // Finalize the test report by logging pass/fail status if
	 * (result.getStatus() == org.testng.ITestResult.FAILURE) {
	 * test.fail("Test failed due to: " + result.getThrowable()); } else {
	 * test.pass("Test passed successfully"); } }
	 */

    @AfterTest
    public void tearDown() {
        if (driver != null) {
        	logger.info("Test execution completed. Closing the browser.");
        	System.out.println("Test execution completed. Closing the browser.");
            //driver.quit();  // Quit WebDriver after test
        }
      }
    
    
    
}




