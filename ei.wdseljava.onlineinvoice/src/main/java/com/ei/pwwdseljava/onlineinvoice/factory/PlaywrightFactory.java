
package com.ei.pwwdseljava.onlineinvoice.factory;

import com.ei.pwwdseljava.onlineinvoice.config.ConfigReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

	private static Playwright playwright;
	private static Browser browser;
	private static BrowserContext context;
	private static Page page;

	public static void initBrowser(String browserName) {
		System.setProperty("PLAYWRIGHT_DISABLE_USAGE_REPORTING", "1");
		System.setProperty("PLAYWRIGHT_SKIP_VALIDATE_HOST_REQUIREMENTS", "true");

		playwright = Playwright.create();
		BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
		boolean headless = ConfigReader.getBoolean("headless");
		options.setHeadless(headless);

		switch (browserName.toLowerCase()) {
		case "chrome":
			options.setChannel("chrome");
			browser = playwright.chromium().launch(options);
			// new
			// BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome").setArgs(List.of("--incognito"));
			break;
		case "edge":
			options.setChannel("msedge");
			browser = playwright.chromium().launch(options);
			break;
		case "chromium":
			browser = playwright.chromium().launch(options);
			break;
		case "firefox":
			browser = playwright.firefox().launch(options);
			break;
		case "webkit":
			browser = playwright.webkit().launch(options);
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browserName);
		}

		/*
		 * Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
		 * 
		 * // Optional: Turn off HAR or tracing by avoiding any HAR setup // This also
		 * avoids Playwright trying to access 'package.json' context =
		 * browser.newContext(contextOptions);
		 */
		Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
		contextOptions.setRecordHarPath(null); // explicitly avoid HAR
		contextOptions.setRecordVideoDir(null); // avoid video
		context = browser.newContext(contextOptions);

		page = context.newPage();

		System.out.println("Playwright initialized: " + browserName + " | Headless: " + headless);
	}

	public static Playwright getPlaywright() {
		return playwright;
	}

	public static Browser getBrowser() {
		return browser;
	}

	public static BrowserContext getContext() {
		return context;
	}

	public static Page getPage() {
		return page;
	}

	public static void closeAll() {
		if (page != null) {
			page.close();
		}
		if (context != null) {
			context.close();
		}
		if (browser != null) {
			// browser.close();
		}
		if (playwright != null) {
			// playwright.close();
		}
	}
}
