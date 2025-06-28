package com.ei.pwwdseljava.onlineinvoice.objects;

public class InvoiceEntryObjects {

	// Select practice and practitioner
	
	public static final String PAYMENT_LINK_CSS = "a[routerlink='/payment']";
	public static final String PRACTICE_DROPDOWN_CSS = "mat-select[formcontrolname='selectedAccount']";
	public static final String PRACTICE_OPTION_XPATH_PATTERN = "//span[normalize-space()='%s']";
	public static final String PRACTITIONER_DROPDOWN_CSS = "mat-select[formcontrolname='selectedPractitioner']";
	public static final String PRACTITIONER_OPTION_XPATH_PATTERN = "//span[normalize-space()='%s']";
	public static final String NEXT_BUTTON_XPATH = "//button[@matsteppernext and contains(., 'Next')]";

	// Select Patient
    public static final String PATIENT_SEARCH_INPUT_XPATH = "//input[@formcontrolname='patientSearch']";
    public static final String PATIENT_SEARCH_BUTTON_XPATH = "//button[.//mat-icon[text()='search']]";
    public static final String PATIENT_CARD_XPATH = "//form[@name='patientFormGroup']//mat-card-content[@class='mat-card-content']";
    public static final String PATIENT_NEXT_BUTTON_XPATH = "//form[@name='patientFormGroup']//span[contains(normalize-space(text()), 'Next')]";

    // Select Item
    public static final String ITEM_SEARCH_INPUT_XPATH = "//input[@formcontrolname='itemSearch']";
    public static final String ITEM_SEARCH_BUTTON_XPATH = "//form[@name='itemFormGroup']//button//mat-icon[contains(normalize-space(text()), 'search')]";
    public static final String ITEM_DROPDOWN_BOX_XPATH = "//form[@name='itemFormGroup']//mat-panel-title";
    public static final String ITEM_DESCRIPTION_BOX_XPATH = "//form[@name='itemFormGroup']//textarea";
    public static final String ITEM_ADD_BUTTON_XPATH = "//form[@name='itemFormGroup']//button/span[contains(normalize-space(text()), 'Add Item')]";
    public static final String ITEM_NEXT_BUTTON_XPATH = "//form[@name='itemFormGroup']//button/span[contains(normalize-space(text()), 'Next')]";
}