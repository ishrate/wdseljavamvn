package com.ei.wdseljava.onlineinvoice.pages;

import com.ei.wdseljava.onlineinvoice.objects.LoginObjects;
import com.ei.wdseljava.onlineinvoice.tests.BaseTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	
	 private WebDriver driver;
	 
	  // Constructor to initialize WebDriver 
	 public LoginPage(WebDriver driver) {
	  this.driver = driver; }
	 

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
    public void login(String url, String username, String password) {
    	driver.get(url);
    	
    	System.out.println("indide login, browser launced..");
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    	
    	System.out.println("Username is "+ username);
        enterUsername(username);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        enterPassword(password);
    	
        
        System.out.println("Button is about to clicked");
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        clickLoginButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
        
        clickCloseButton();
        
        
    }
}
