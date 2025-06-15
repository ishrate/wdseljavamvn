package com.ei.wdseljava.onlineinvoice.tests;

import com.ei.wdseljava.onlineinvoice.data.DataProviderUtil;
import com.ei.wdseljava.onlineinvoice.pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class MyFirstTest extends BaseTest {
	

    // DataProvider to get login data from config.properties via DataProviderUtil
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        // Fetch data from DataProviderUtil
        return DataProviderUtil.loadLoginDataFromConfig();
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String url, String username, String password) {
        // Open the login page
        //driver.get("http://example.com/login"); // Replace with your login URL

        // Create an instance of LoginPage
    	System.out.println(url);
     	System.out.println(username);
     	System.out.println(password);
    	
    	 System.out.println("Inside testlogin.");
        LoginPage loginPage = new LoginPage(driver);
        
        
   	 System.out.println("we are going to go inside Loginpage");

        // Perform login using the data from the data provider
        loginPage.login(url, username, password);
        
        System.out.println("Evrtything should be done by this time ");

        // Add assertions to verify the login was successful (example)
        // Example assertion: Assert.assertTrue(driver.getTitle().contains("Dashboard"));

       // driver.quit();
    }
}
