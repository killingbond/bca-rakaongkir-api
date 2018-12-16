package com.cus.jastip.bca.domain;

import java.util.List;

public class ResultProvinceModel {

	private List<ProvinceModel> results;

	public List<ProvinceModel> getResults() {
		return results;
	}

	public void setResults(List<ProvinceModel> results) {
		this.results = results;
	}

	public ResultProvinceModel(List<ProvinceModel> results) {
		super();
		this.results = results;
	}

	public ResultProvinceModel() {
		super();
	}

}
