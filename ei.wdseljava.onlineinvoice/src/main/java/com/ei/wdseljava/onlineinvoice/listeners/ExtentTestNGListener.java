package com.ei.wdseljava.onlineinvoice.listeners;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.ei.wdseljava.onlineinvoice.utils.ExtentManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test log entry for the test
        test = ExtentManager.getReporterInstance().createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log test success
        test.log(Status.PASS, "Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log test failure with exception details
        test.log(Status.FAIL, "Test failed due to: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log test skip
        test.log(Status.SKIP, "Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used in this case
    }

    @Override
    public void onStart(ITestContext context) {
        // Not needed for this case
    }

    @Override
    public void onFinish(ITestContext context) {
        // Ensure that the report is flushed after all tests are finished
        ExtentManager.flush();
    }
}