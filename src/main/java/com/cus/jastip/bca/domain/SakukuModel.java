package com.cus.jastip.bca.domain;

public class SakukuModel {
	private String MerchantID;
	private String MerchantName;
	private String Amount;
	private String Tax;
	private String TransactionID;
	private String CurrencyCode;
	private String RequestDate;
	private String ReferenceID;

	public SakukuModel(String merchantID, String merchantName, String amount, String tax, String transactionID,
			String currencyCode, String requestDate, String referenceID) {
		super();
		MerchantID = merchantID;
		MerchantName = merchantName;
		Amount = amount;		
		Tax = tax;
		TransactionID = transactionID;
		CurrencyCode = currencyCode;
		RequestDate = requestDate;
		ReferenceID = referenceID;
	}

	public SakukuModel() {
		super();
	}

	public String getMerchantID() {
		return MerchantID;
	}

	public void setMerchantID(String merchantID) {
		MerchantID = merchantID;
	}

	public String getMerchantName() {
		return MerchantName;
	}

	public void setMerchantName(String merchantName) {
		MerchantName = merchantName;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTax() {
		return Tax;
	}

	public void setTax(String tax) {
		Tax = tax;
	}

	public String getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}

	public String getCurrencyCode() {
		return CurrencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

	public String getRequestDate() {
		return RequestDate;
	}

	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}

	public String getReferenceID() {
		return ReferenceID;
	}

	public void setReferenceID(String referenceID) {
		ReferenceID = referenceID;
	}

}
