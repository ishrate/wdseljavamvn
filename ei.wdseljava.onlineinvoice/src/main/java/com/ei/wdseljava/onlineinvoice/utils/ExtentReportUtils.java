package com.ei.wdseljava.onlineinvoice.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReportUtils {

	/*
	 * private static ExtentReports extent; private static ExtentTest test; private
	 * static final Logger logger = LogManager.getLogger(ExtentReportUtils.class);
	 */

	/*
	 * static { String reportPath = "target/reports/extent-report.html";
	 * ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
	 * sparkReporter.config().setReportName("Automation Test Results");
	 * sparkReporter.config().setDocumentTitle("Test Execution Report");
	 * 
	 * extent = new ExtentReports(); extent.attachReporter(sparkReporter); }
	 */
    
 // Attach screenshot to report on failure
	/*
	 * public static void addScreenshotToReport(WebDriver driver, ExtentTest test)
	 * throws IOException { // Capture screenshot String screenshotPath =
	 * captureScreenshot(driver);
	 * 
	 * // Attach screenshot to the report
	 * test.fail("Test failed. See screenshot below.",
	 * MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()); }
	 * 
	 * private static String captureScreenshot(WebDriver driver) { // Take
	 * screenshot and store it in a file TakesScreenshot screenshot =
	 * (TakesScreenshot) driver; String screenshotPath =
	 * "test-output/ExtentReports/screenshots/" + System.currentTimeMillis() +
	 * ".png";
	 * 
	 * // Save screenshot file to the specified path
	 * screenshot.getScreenshotAs(OutputType.FILE).renameTo(new
	 * java.io.File(screenshotPath)); return screenshotPath;
	 * 
	 * }
	 */
    
	/*
	 * public static void addCustomInfo(ExtentTest test) {
	 * test.info("Test executed on: " + System.getProperty("os.name"));
	 * test.info("Test started at: " + System.currentTimeMillis()); }
	 * 
	 * 
	 * 
	 * public static void startTest(String testName, String description) { test =
	 * extent.createTest(testName, description); }
	 * 
	 * public static void logInfo(String message) { if (test != null) {
	 * test.info(message); logger.info(message); } }
	 * 
	 * public static void logPass(String message) { if (test != null) {
	 * test.pass(message); logger.info(message); } }
	 * 
	 * public static void logFail(String message) { if (test != null) {
	 * test.fail(message); logger.error(message); }
	 */
	 
	/*
	 * }
	 * 
	 * public static void flush() { if (extent != null) { extent.flush();
	 * logger.info("Extent report generated successfully."); } }
	 * 
	 * public static void sendEmailReport() {
	 * logger.info("Sending the test report via email..."); }
	 */
}
