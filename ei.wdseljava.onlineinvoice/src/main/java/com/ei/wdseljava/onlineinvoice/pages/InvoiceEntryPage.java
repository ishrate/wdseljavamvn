package com.ei.wdseljava.onlineinvoice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InvoiceEntryPage {
    WebDriver driver;

    private By invoiceField = By.id("invoice-field");

    public InvoiceEntryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterInvoiceDetails(String invoiceDetails) {
        driver.findElement(invoiceField).sendKeys(invoiceDetails);
    }
}
