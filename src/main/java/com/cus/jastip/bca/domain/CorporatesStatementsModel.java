package com.cus.jastip.bca.domain;

public class CorporatesStatementsModel {
	private String startDate;
	private String endDate;
	private String currency;
	private String startBalance;
	private DataModel data;

	public CorporatesStatementsModel() {
		super();
	}

	public CorporatesStatementsModel(String startDate, String endDate, String currency, String startBalance,
			DataModel data) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.currency = currency;
		this.startBalance = startBalance;
		this.data = data;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(String startBalance) {
		this.startBalance = startBalance;
	}

	public DataModel getData() {
		return data;
	}

	public void setData(DataModel data) {
		this.data = data;
	}

}
