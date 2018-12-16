package com.cus.jastip.bca.domain;

public class RajaOngkirCostModel {

	private Integer origin;
	private Integer destination;
	private Integer weight;
	private String Courier;

	public RajaOngkirCostModel() {
		super();
	}

	public RajaOngkirCostModel(Integer origin, Integer destination, Integer weight, String courier) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		Courier = courier;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getCourier() {
		return Courier;
	}

	public void setCourier(String courier) {
		Courier = courier;
	}

}
