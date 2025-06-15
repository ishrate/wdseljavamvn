package com.ei.wdseljava.onlineinvoice.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {

    private static ExtentReports extent;
   // private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
   
    
    public static ExtentReports getReporterInstance() {
       // if (extent == null) {
            // Set the report file name and path
            String reportPath = "test-output/ExtentReports/ExtentReport_" +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
            
            // Use ExtentSparkReporter instead of ExtentHtmlReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // Set report configuration (optional)
            extent.setSystemInfo("OS", "Windows 10");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Tester", "Eamon");
            
            sparkReporter.config().setDocumentTitle("Selenium Extent Report");
            sparkReporter.config().setReportName("Functional Test Report");
            sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
        //}
        return extent;
    }
    
    public static void captureScreenshot(WebDriver driver, ExtentTest test) {
        try {
            // Take screenshot and save it
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);

            // Generate a timestamped path for the screenshot
            String destPath = "test-output/ExtentReports/screenshots/" + 
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
            File destination = new File(destPath);

            // Copy the screenshot to the destination
            org.apache.commons.io.FileUtils.copyFile(source, destination);

            // Attach the screenshot to the extent report
            test.fail("Test failed. See screenshot below:", 
                      MediaEntityBuilder.createScreenCaptureFromPath(destPath).build());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/*
	 * public static ExtentTest startTest(String testName, String description) {
	 * ExtentTest extentTest = extent.createTest(testName, description);
	 * test.set(extentTest); return extentTest; }
	 */

    public static void flush() {
    	  if (extent != null) {
              extent.flush();
          }
    }
}
