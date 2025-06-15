package com.ei.wdseljava.onlineinvoice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    private By invoiceEntryButton = By.id("invoice-entry");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToInvoiceEntryPage() {
        driver.findElement(invoiceEntryButton).click();
    }
}
