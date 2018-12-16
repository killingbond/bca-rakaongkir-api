package com.cus.jastip.bca.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProvinceModel {

	@Id
	private String province_id;
	private String province;

	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String province_id, String province) {
		super();
		this.province_id = province_id;
		this.province = province;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
