package com.ei.pwwdseljava.onlineinvoice.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.ei.pwwdseljava.onlineinvoice.objects.LoginObjects;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;

public class CbLogin {

	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(CbLogin.class);
	private ExtentTest test;

	// Constructor to initialize WebDriver
	public CbLogin(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	// Method to input username
	public void enterUsername(String username) {
		WebElement usernameTextbox = driver.findElement(By.xpath(LoginObjects.USERNAME_TEXTBOX));
		usernameTextbox.sendKeys(username);
	}

	// Method to input password
	public void enterPassword(String password) {
		WebElement passwordTextbox = driver.findElement(By.xpath(LoginObjects.PASSWORD_TEXTBOX));
		passwordTextbox.sendKeys(password);
	}

	// Method to click login button
	public void clickLoginButton() {
		WebElement loginButton = driver.findElement(By.xpath(LoginObjects.LOGIN_BUTTON));
		loginButton.click();
	}

	// Method to click login button
	public void clickCloseButton() {
		WebElement closeButton = driver.findElement(By.xpath(LoginObjects.CLOSE_BUTTON));
		closeButton.click();
	}

	// Method to perform login
	public void login(String url, String username, String password) throws Exception {
		try {
			test.info("Navigating to URL: " + url);
			driver.get(url);
			logger.info("Browser launched and navigated to URL: {}", url);
			System.out.println("-->Browser launched and navigated to URL: " + url);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			test.info("Entering username: " + username);
			enterUsername(username);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			test.info("Entering password.");
			enterPassword(password);

			test.info("Clicking login button.");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			clickLoginButton();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

			test.info("Clicking close button.");

			Thread.sleep(4000); // Optional: replace with explicit wait in real tests

			// New popup
			By closeBtnLocator = By.xpath("//button[normalize-space()='Close']");

			// Find elements
			List<WebElement> closeButtons = driver.findElements(closeBtnLocator);

			if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
				closeButtons.get(0).click();
				System.out.println("Popup closed successfully.");
			} else {
				System.out.println("No popup found or button not visible.");
			}
                        //Old popup
			Thread.sleep(4000);
			By closeSpan = By.xpath("//span[contains(@class,'walkme-action-destroy') and text()='Close']");

			List<WebElement> closeElements = driver.findElements(closeSpan);

			if (!closeElements.isEmpty() && closeElements.get(0).isDisplayed()) {
				closeElements.get(0).click();
				System.out.println("popup closed.");
			} else {
				System.out.println("popup not present.");
			}

			// clickCloseButton();
			Thread.sleep(4000); // Optional: replace with explicit wait in real tests

			// XPath with text match and dynamic structure

			logger.info("Login process completed successfully.");
			test.pass("Login process completed successfully.");
		} catch (Exception e) {
			logger.error("Error during login: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Login failed: " + e.getMessage());
			System.out.println("-->Error during login: " + e.getMessage());
			throw e;
		}
	}
}
