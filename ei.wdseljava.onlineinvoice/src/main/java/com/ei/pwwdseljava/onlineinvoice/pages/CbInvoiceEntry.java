package com.ei.pwwdseljava.onlineinvoice.pages;

import static com.ei.pwwdseljava.onlineinvoice.objects.InvoiceEntryObjects.*;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.ei.pwwdseljava.onlineinvoice.config.BaseClass;
import com.ei.pwwdseljava.onlineinvoice.utils.ExtentManager;

public class CbInvoiceEntry extends BaseClass {
	private WebDriver driver;
	private WebDriverWait wait;

	private static final Logger logger = LoggerFactory.getLogger(CbInvoiceEntry.class);
	private ExtentTest test;

	public CbInvoiceEntry(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.test = test;
	}

	public void selectPractice(String practiceName, String practitionerName) throws InterruptedException {
		try {
			test.info("Selecting practice: and practitioner" + practiceName + " and " + practitionerName);
			logger.info("Selecting practice: {} {}", practiceName, practitionerName);
			System.out.println("-->Driver available? " + (driver != null));

			Thread.sleep(4000);
			System.out.println("-->Navigating to payment link");
			WebElement paymentLink = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector(PAYMENT_LINK_CSS)));
			paymentLink.click();
			logger.info("Payment link clicked");
			System.out.println("-->Payment link clicked");
			sleep(4000);

			WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(PRACTICE_DROPDOWN_CSS)));
			Actions actions = new Actions(driver);
			actions.moveToElement(dropdown).click().perform();
			System.out.println("-->Overlay Dropdown clicked");

			sleep(2000);

			WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(PRACTICE_OPTION_XPATH_PATTERN, practiceName))));
			option.click();
			logger.info("Practice selected: {}", practiceName);
			System.out.println("-->Practice selected: " + practiceName);

			sleep(2000);

			WebElement dropdowntwo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(PRACTITIONER_DROPDOWN_CSS)));
			Actions actionstwo = new Actions(driver);
			actionstwo.moveToElement(dropdowntwo).click().perform();
			logger.info("Overlay Dropdown clicked");
			System.out.println("-->Overlay Dropdown clicked");

			sleep(2000);

			WebElement optiontwo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(PRACTITIONER_OPTION_XPATH_PATTERN, practitionerName))));
			optiontwo.click();
			logger.info("Practioner selected: {}", practitionerName);
			System.out.println("->Practioner selected: " + practitionerName);

			sleep(3000);

			WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NEXT_BUTTON_XPATH)));
			nextBtn.click();
			sleep(2000);

			System.out.println("-->Practice and Practitioner selected successfully: " + practiceName + " and " + practitionerName);
			test.pass("Practice and Pratitioner selected successfully: " + practiceName);

		} catch (Exception e) {
			logger.error("Error in selectPractice: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Error in selectPractice: " + e.getMessage());
			throw e;
		}
	}

	public void selectPatient(String patientName) throws InterruptedException {
		try {
			test.info("Selecting patient: " + patientName);
			logger.info("Selecting patient: {}", patientName);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PATIENT_SEARCH_INPUT_XPATH)));
			inputBox.click();
			inputBox.clear();
			inputBox.sendKeys(patientName);
			sleep(2000);

			WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PATIENT_SEARCH_BUTTON_XPATH)));
			searchBtn.click();

			Thread.sleep(3000);

			List<WebElement> cards = driver.findElements(By.xpath(PATIENT_CARD_XPATH));

			WebElement firstCard = cards.get(0);
			wait.until(ExpectedConditions.elementToBeClickable(firstCard));
			firstCard.click();
			Thread.sleep(4000);

			WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(PATIENT_NEXT_BUTTON_XPATH)));
			nextBtn.click();
			Thread.sleep(2000);

			System.out.println("-->Patient selected: " + patientName);
			test.pass("Patient selected successfully: " + patientName);
		} catch (Exception e) {
			logger.error("Error in selectpatient: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Error in selectpatient: " + e.getMessage());
			throw e;
		}
	}

	public void selectItem(String itemName) {
		try {
			test.info("Selecting item: " + itemName);
			logger.info("Selecting item: {}", itemName);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			WebElement inputBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_SEARCH_INPUT_XPATH)));
			inputBox.click();
			inputBox.clear();
			inputBox.sendKeys(itemName);
			sleep(2000);

			WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_SEARCH_BUTTON_XPATH)));
			searchBtn.click();

			sleep(3000);

			WebElement dropDownBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_DROPDOWN_BOX_XPATH)));
			dropDownBox.click();
			sleep(2000);

			WebElement descriptionBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_DESCRIPTION_BOX_XPATH)));
			descriptionBox.click();
			descriptionBox.clear();
			descriptionBox.sendKeys(itemName);
			sleep(2000);

			WebElement addItemBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_ADD_BUTTON_XPATH)));
			addItemBtn.click();
			sleep(2000);

			WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ITEM_NEXT_BUTTON_XPATH)));
			nextBtn.click();

		} catch (Exception e) {
			logger.error("Error in selectItem: {}", e.getMessage(), e);
			ExtentManager.captureScreenshot(driver, test);
			test.fail("Error in selectItem: " + e.getMessage());
			throw e;
		}
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}