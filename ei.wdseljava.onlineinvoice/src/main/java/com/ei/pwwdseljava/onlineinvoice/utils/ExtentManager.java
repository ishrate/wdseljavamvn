package com.ei.pwwdseljava.onlineinvoice.utils;

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
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ei.pwwdseljava.onlineinvoice.config.ConfigReader;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getReporterInstance() {
		if (extent == null) {
			String reportPath = ConfigReader.getReportPath() + "/ExtentReport.html";

			File reportDir = new File(reportPath).getParentFile();
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("Browser", "Chrome");
			extent.setSystemInfo("Tester", "Eamon Ishrat");

			sparkReporter.config().setDocumentTitle("Online Invoice Test Report");
			sparkReporter.config().setReportName("Sanity Test Report");
			sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
		}
		return extent;
	}

	public static void captureScreenshot(WebDriver driver, ExtentTest test) {
		try {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File source = screenshot.getScreenshotAs(OutputType.FILE);

			// Set the screenshot directory under test-output
			String screenshotDir = "test-output/screenshots";
			new File(screenshotDir).mkdirs();

			String destPath = screenshotDir + "/screenshot_"
					+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";

			File destination = new File(destPath);
			org.apache.commons.io.FileUtils.copyFile(source, destination);

			test.fail("Test failed. See screenshot below:",
					MediaEntityBuilder.createScreenCaptureFromPath(destPath).build());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void flush() {
		if (extent != null) {
			extent.flush();
		}
	}
}