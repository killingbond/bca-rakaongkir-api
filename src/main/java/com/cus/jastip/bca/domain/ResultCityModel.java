package com.cus.jastip.bca.domain;

import java.util.List;

public class ResultCityModel {

	private List<CityModel> results;

	public ResultCityModel() {
		super();
	}

	public ResultCityModel(List<CityModel> results) {
		super();
		this.results = results;
	}

	public List<CityModel> getResults() {
		return results;
	}

	public void setResults(List<CityModel> results) {
		this.results = results;
	}

}
