package com.ei.pwwdseljava.onlineinvoice.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

	public static WebElement waitForElement(WebDriver driver, By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForPageToLoad(WebDriver driver, int timeoutInSeconds) {
		try {
			new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutInSeconds))
					.until(webDriver -> "complete"
							.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred while waiting for the page to load: " + e.getMessage());
		}
	}

	public static WebElement waitForElementjScriptExec(WebDriver driver, By locator, Duration timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			// Scroll to the element if not in the viewport
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			return element;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurred while waiting for the element: " + e.getMessage());
			throw e;
		}
	}
}
