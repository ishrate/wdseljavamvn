package com.ei.pwwdseljava.onlineinvoice.objects;

public class QuoteInvoiceSubmitObjects {

	// XPath for 'Check Eligibility' button
	public static final String CHECK_ELIGIBILITY_BUTTON_XPATH = "//app-payment//app-invoice-create//button/span[contains(normalize-space(text()), 'Check Eligibility')]";

	// XPath for Claim Result span
	public static final String CLAIM_RESULT_SPAN_XPATH = "//app-home//app-payment//app-invoice-create//div//label[contains(normalize-space(text()), 'Claim Result')]/following-sibling::span";

	// XPath for 'Process Invoice' button
	public static final String PROCESS_INVOICE_BUTTON_XPATH = "//app-payment//app-invoice-create//button/span[contains(normalize-space(text()), 'Process Invoice')]";

	// XPath for Invoice Result elements
	public static final String INVOICE_RESULT_ELEMENTS_XPATH = "//app-home//app-invoice-processing//app-invoice-details//label[contains(normalize-space(text()), 'Claim Result')]/following-sibling::label";

	// XPath for Invoice Number
	public static final String INVOICE_NUMBER_XPATH = "//app-home//app-invoice-processing//app-invoice-details//label[contains(normalize-space(text()), 'Invoice No')]/following-sibling::label";

	// XPath for Claim Number
	public static final String CLAIM_NUMBER_XPATH = "//app-home//app-invoice-processing//app-invoice-details//label[contains(normalize-space(text()), 'WorkSafe Victoria Claim Number')]/following-sibling::label";
}