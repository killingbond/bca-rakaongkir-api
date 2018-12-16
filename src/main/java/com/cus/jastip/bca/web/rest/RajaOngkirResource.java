package com.cus.jastip.bca.web.rest;

import java.io.IOException;

import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cus.jastip.bca.domain.RajaOngkirCityModel;
import com.cus.jastip.bca.domain.RajaOngkirCostModel;

import com.cus.jastip.bca.domain.RajaOngkirModel;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;



@RestController
@RequestMapping("/api/rajaongkir")
public class RajaOngkirResource {

	private final String apikey = "6dfd942ac299210748b305f3cda26c6c";

	

	@PostMapping("/cost")
	public String getCost() {
		RajaOngkirCostModel raja = new RajaOngkirCostModel();
		raja.setOrigin(501);
		raja.setDestination(101);
		raja.setWeight(1);
		raja.setCourier("jne");
		String jsonstr = convertJsonToString(raja);
		String result = "";

		HttpResponse response;
		try {
			response = Request.Post("https://api.rajaongkir.com/starter/cost")
					.addHeader("Content-Type", "application/json").addHeader("Key", apikey)
					.bodyString(jsonstr, ContentType.APPLICATION_JSON).execute().returnResponse();
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	@GetMapping("/province")
	public RajaOngkirModel getProvince() throws ClientProtocolException, IOException {

		HttpResponse response = Request.Get("https://api.rajaongkir.com/starter/province").addHeader("Key", apikey)
				.execute().returnResponse();

		RajaOngkirModel result = new Gson().fromJson(EntityUtils.toString(response.getEntity()), RajaOngkirModel.class);

		/*
		 * for (ProvinceModel provinceModel : result.getRajaongkir().getResults()) {
		 * provinceRepository.save(provinceModel); }
		 */
		return result;

	}

	@GetMapping("/city")
	public RajaOngkirCityModel getCity() throws ClientProtocolException, IOException {

		HttpResponse response = Request.Get("https://api.rajaongkir.com/starter/city").addHeader("Key", apikey)
				.execute().returnResponse();

		RajaOngkirCityModel result = new Gson().fromJson(EntityUtils.toString(response.getEntity()),
				RajaOngkirCityModel.class);

		/*
		 * for (CityModel provinceModel : result.getRajaongkir().getResults()) {
		 * cityRepository.save(provinceModel); }
		 */

		return result;

	}

	public String convertJsonToString(Object object) {
		// Mapping json jadi string
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonstr = "";
		try {
			jsonstr = mapperObj.writeValueAsString(object);
			// Mapping Strin depan jadi uppercase

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jsonstr;
	}

}
