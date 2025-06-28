package com.ei.pwwdseljava.onlineinvoice.pages;

import java.nio.file.Paths;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class CbInvoiceEntryPage {
	private Page page;

	public CbInvoiceEntryPage(Page page) {
		this.page = page;
	}

	public void selectPractice(String practiceName) {
		System.out.println("Page available? " + (page != null));

		// Ensure the page is fully loaded before interacting
		page.waitForLoadState(LoadState.NETWORKIDLE);
		System.out.println("Page loaded successfully");
		// Click on the Payment link to navigate to the invoice entry page
		System.out.println("Clicking on Payment Link, Navigating to invoice entry page");
		System.out.println("Selecting practice: " + practiceName);

		Locator paymentLink = page.locator("a[routerlink='/payment']");
		paymentLink.waitFor(); // waits until the element is attached and visible
		paymentLink.click();
		page.waitForTimeout(4000);

		System.out.println("Payment link clicked, now the practice box should be visible");

		/*
		 * Locator practice =
		 * page.locator("mat-select[formcontrolname='selectedAccount']");
		 * 
		 * practice.waitFor(); // waits until the element is attached and visible
		 * practice.click();
		 * 
		 */

		// System.out.println("Trying to bring up dropdown after clicking on
		// combobox...");
		// page.screenshot(new
		// Page.ScreenshotOptions().setPath(Paths.get("beforeclickingonCombo.png")));
		System.out.println("clicking on  combobox...");
		// Step 1: Click the dropdown forcefully
		page.locator("mat-select[formcontrolname='selectedAccount']").click(new Locator.ClickOptions().setForce(true));
		System.out.println("Practice dropdown clicked, now the options should be visible");

		// page.screenshot(new
		// Page.ScreenshotOptions().setPath(Paths.get("afterclickingonCombo.png")));

		// Step 2: Wait for the options to appear inside the overlay
		/*
		 * page.waitForSelector(".cdk-overlay-container .mat-option", new
		 * Page.WaitForSelectorOptions().setTimeout(5000));
		 * 
		 * // Optional: Take screenshot for debugging page.screenshot(new
		 * Page.ScreenshotOptions().setPath(Paths.get("debug_after_click.png")));
		 * 
		 * // Step 3: Select the option by visible text
		 * page.locator(".mat-option >> text='PURE SKIN CLINIC PTY LTD'").click();
		 */

		/*
		 * Locator comboBox = page.locator("mat-select[formcontrolname='practice']");
		 * BoundingBox box = comboBox.boundingBox();
		 * 
		 * if (box != null) { double centerX = box.x + box.width / 2; double centerY =
		 * box.y + box.height / 2; page.mouse().click(centerX, centerY);
		 * 
		 * page.waitForTimeout(1000); }
		 */
		System.out.println("Box Clicked, Now ....After clicking on combobox");

		page.waitForTimeout(2000);
		page.evaluate("document.querySelector('mat-select[formcontrolname=\"practice\"]').focus()");
		page.waitForTimeout(2000);
		page.keyboard().press("Enter");
		page.waitForTimeout(1000);
		System.out.println("Trying .....testing the dropdown options");

		/*
		 * page.keyboard().press("ArrowDown"); page.waitForTimeout(1000);
		 * page.keyboard().press("ArrowDown"); page.waitForTimeout(1000);
		 * page.keyboard().press("ArrowDown"); page.waitForTimeout(1000);
		 * page.keyboard().press("ArrowDown"); page.waitForTimeout(1000);
		 * page.keyboard().press("Enter");
		 */

		// page.waitForTimeout(500); // slight wait
		// page.keyboard().press("Enter"); // simulate Enter key
		page.waitForTimeout(2000); // allow time for dropdown to appear

		// page.locator(".mat-option >> text='PURE SKIN CLINIC PTY LTD'").click();

		// Locator option = page.locator("mat-option span:text('PURE SKIN CLINIC PTY
		// LTD')");

		Locator option = page.locator("xpath=//span[normalize-space()='PURE SKIN CLINIC PTY LTD']");
		option.click();
		// page.waitForTimeout(4000);

		// page.keyboard().type("Pure");
		// page.waitForTimeout(800); // Wait for UI to filter the options
		// page.keyboard().press("Enter");

		page.waitForTimeout(4000);
		/*
		 * 
		 * Locator options = page.locator("mat-option"); options.filter(new
		 * Locator.FilterOptions().setHasText(practiceName)).first().click();
		 */
		System.out.println("SUCCESS after slecting option from dropdown");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("afterselectoption.png")));

		System.out.println("✅ Practiceiiiiii selected: " + practiceName);
	}

	// Add more form actions here if needed
}// }