package com.ei.pwwdseljava.onlineinvoice.pages;

import com.ei.pwwdseljava.onlineinvoice.config.ConfigReader;
import com.microsoft.playwright.Page;

public class CbLoginPage {
	private final Page page;

	// Constructor
	public CbLoginPage(Page page) {
		this.page = page;
	}

	// Locators
	private final String usernameInput = "#input_username";
	private final String passwordInput = "#Input_Password";
	private final String loginButton = "button[type='submit']";

	// Actions
	public void login(String string1, String string2) {

		System.out.println("Inside login function Login URL: " + ConfigReader.get("login.url"));
		System.out.println("Page available? " + (page != null));
		page.fill(usernameInput, string1);
		page.fill(passwordInput, string2);
		page.click(loginButton);
		System.out.println("Login button clicked");
		page.waitForTimeout(5000); // Wait for 5 seconds to ensure login completes

		// Try closing the popup safely
		if (page.isVisible("//*[@id=\"wm-shoutout-558497\"]/div[3]/div[2]/span")) {
			System.out.println("Popup detected. Attempting to close.");
			page.waitForSelector("//*[@id=\"wm-shoutout-558497\"]/div[3]/div[2]/span",
					new Page.WaitForSelectorOptions().setTimeout(5000));
			page.click("//*[@id=\"wm-shoutout-558497\"]/div[3]/div[2]/span");
		} else {
			System.out.println("No popup found after login.");
		}

		System.out.println("Login completed successfully. Now on the main page.");
	}
}
