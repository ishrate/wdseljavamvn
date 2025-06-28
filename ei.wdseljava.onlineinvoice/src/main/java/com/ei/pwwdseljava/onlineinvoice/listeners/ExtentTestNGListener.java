package com.ei.pwwdseljava.onlineinvoice.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;

public class ExtentTestNGListener implements ITestListener {

	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = ExtentManager.getReporterInstance().createTest(result.getName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, "Test passed successfully");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, "Test failed: " + result.getThrowable());

		// Try to capture screenshot using WebDriver from test class
		Object testClass = result.getInstance();
		try {
			WebDriver driver = (WebDriver) testClass.getClass().getMethod("getDriver").invoke(testClass);
			ExtentManager.captureScreenshot(driver, test.get());
		} catch (Exception e) {
			test.get().log(Status.WARNING, "Could not capture screenshot: " + e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, "Test skipped: " + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Optional
	}

	@Override
	public void onStart(ITestContext context) {
		// Optional
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flush();
	}
}
