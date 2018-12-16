package com.cus.jastip.bca.web.rest;

import com.cus.jastip.bca.domain.Corporates;
import com.cus.jastip.bca.domain.SakukuModel;
import com.cus.jastip.bca.domain.Signature;
import com.cus.jastip.bca.domain.SignatureHeader;
import com.cus.jastip.bca.domain.Token;
import com.cus.jastip.bca.domain.TransfersModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;



import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bca")
public class HitResource {

	private static final String bcaKey = "3a3add4e-3df7-494f-9ed1-d92b831cbeb3";
	private static final String bcaSecret = "5c5d3242-559a-409f-bec7-aae07ef1d22d";
	private static final String sendbox = "https://sandbox.bca.co.id";
	private static final String generalInfoBca = "/general/info-bca/atm";
	private static final String generalInfoBcaBranch = "/general/info-bca/branch";
	private static final String client_id = "45108128-f5d1-4e71-a2b2-58d63891cd1a";
	private static final String secret_key = "038887c4-f660-4ff5-88cd-d4520f7caed8";
	private static final String sakukuPayment = "/sakuku-commerce/payments";
	private static final String bankCorpTrans = "/banking/corporates/transfers";
	private static final String bankingV3Crops = "/banking/v3";
	private static final String generalRateDeposit = "/general/rate/deposit";
	private static final String generalRateForex = "/general/rate/forex";

	// POST api/oauth/token

	@PostMapping("/token")
	public Token postToken() {
		try {
			String outh = client_id + ":" + secret_key;
			String encode = Base64.getEncoder().encodeToString(outh.getBytes());
			HttpResponse response = Request.Post("https://sandbox.bca.co.id/api/oauth/token")
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.addHeader("Authorization", "Basic " + encode)
					.bodyForm(Form.form().add("grant_type", "client_credentials").build()).execute().returnResponse();
			Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
			return token;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Token();
	}

	// POST /utilities/signature

	@PostMapping("/signature")
	public Signature getSignature(SignatureHeader signHeader) {
		String timestamp = getDateNow();

		try {
			HttpResponse response = null;
			if (signHeader.getHttpMethod().equals("GET")) {
				response = Request.Post("https://sandbox.bca.co.id/utilities/signature")
						.addHeader("Timestamp", timestamp).addHeader("URI", signHeader.getUrl())
						.addHeader("AccessToken", signHeader.getAccessToken()).addHeader("APISecret", bcaSecret)
						.addHeader("HTTPMethod", signHeader.getHttpMethod()).execute().returnResponse();
			} else {
				response = Request.Post("https://sandbox.bca.co.id/utilities/signature")
						.addHeader("Timestamp", timestamp).addHeader("URI", signHeader.getUrl())
						.addHeader("AccessToken", signHeader.getAccessToken()).addHeader("APISecret", bcaSecret)
						.addHeader("HTTPMethod", signHeader.getHttpMethod())
						.addHeader("Content-Type", "application/json")
						.bodyString(signHeader.getJsonStr(), ContentType.APPLICATION_JSON).execute().returnResponse();
			}

			StringTokenizer st = new StringTokenizer(EntityUtils.toString(response.getEntity()));
			String hmac = "";
			int i = 0;
			int count = st.countTokens();
			while (st.hasMoreElements()) {
				st.nextElement();
				if (i == count - 2) {
					hmac = (String) st.nextElement();
					break;
				}
				i++;
			}
			Signature signature = new Signature(hmac, timestamp);
			return signature;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Signature();

	}

	// GET /general/info-bca/atm

	@GetMapping("/info-bca/atm")
	public String getInfoBcaAtm() throws ClientProtocolException, IOException {
		String searchBy = "Distance";
		String latitude = "-6.1900718";
		String longitude = "106.797190";
		String count = "3";
		String radius = "20";
		String query = "?" + "Radius=" + radius + "&Count=" + count + "&Latitude=" + latitude + "&SearchBy=" + searchBy
				+ "&Longitude=" + longitude;
		String url = generalInfoBca + query;
		String urlSendbox = sendbox + generalInfoBca + query;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(url, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(urlSendbox).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}
		}
		return result;

	}

	// GET /general/info-bca/branch

	@GetMapping("/info-bca/branch")
	public String getInfoBcaAtmBranch() throws ClientProtocolException, IOException {
		String latitude = "-6.137235";
		String longitude = "106.824928";
		String query = "?Latitude=" + latitude + "&Longitude=" + longitude;
		String url = generalInfoBcaBranch + query;
		String urlSendbox = sendbox + generalInfoBcaBranch + query;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(url, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(urlSendbox).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}
		}
		return result;

	}

	// GET /general/rate/deposit

	@GetMapping("/general/rate/deposit")
	public String getGeneralRateDeposit() throws ClientProtocolException, IOException {

		String url = generalRateDeposit;
		String urlSendbox = sendbox + generalRateDeposit;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(url, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(urlSendbox).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}
		}

		return result;
	}

	// GET /general/rate/forex?CurrencyCode=USD,JPY,XXX&RateType=erate,tt,yy
	@GetMapping("/general/rate/forex")
	public String getGeneralRateForex() throws ClientProtocolException, IOException {
		String CurrencyCode = "USD,JPY,XXX";
		String RateType = "erate,tt,yy";
		String query = "?CurrencyCode=" + CurrencyCode + "&RateType=" + RateType;
		String url = generalRateForex + query;
		String urlSendbox = sendbox + url;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(url, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(urlSendbox).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}
		}
		return result;

	}
	// POST /sakuku-commerce/payments

	@PostMapping("/sakuku/payment")
	public String getSakukuPayment() throws ParseException, IOException {
		// sakuku data
		SakukuModel sakukuModel = new SakukuModel();
		sakukuModel.setMerchantID("89000");
		sakukuModel.setMerchantName("Merchant One");
		sakukuModel.setAmount("100.22");
		sakukuModel.setTax("0.0");
		sakukuModel.setTransactionID("156479");
		sakukuModel.setCurrencyCode("IDR");
		sakukuModel.setRequestDate("2015-04-29T09:54:00.234+07:00");
		sakukuModel.setReferenceID("123465798");

		// buat url sakuku
		String urlSendbox = sendbox + sakukuPayment;

		// buat jsonstring
		String jsonstr = convertJsonToString(sakukuModel);
		String result = "";
		// buat token
		Token token = postToken();
		if (token != null) {
			// buat signature
			SignatureHeader signHeader = new SignatureHeader(sakukuPayment, token.getAccess_token(), "POST", jsonstr);
			Signature signature = getSignature(signHeader);

			if (signature != null) {
				HttpResponse response = Request.Post(urlSendbox).addHeader("Content-Type", "application/json")
						.addHeader("X-BCA-KEY", bcaKey).addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac())
						.bodyString(jsonstr, ContentType.APPLICATION_JSON).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());

			}

		}
		return result;

	}

	// GET /sakuku-commerce/payments/{merchantId}/{paymentId}

	@GetMapping("/sakuku/payment")
	public String getSakukuPaymentWithMerchant() throws ParseException, IOException {
		String merchantId = "89000";
		String paymentId = "0FE117D539DF610FE0540021281A5568";
		String sakukuPath = sakukuPayment + "/" + merchantId + "/" + paymentId;
		String url = sendbox + sakukuPayment + "/" + merchantId + "/" + paymentId;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(sakukuPath, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(url).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}

		}
		return result;

	}

	// POST /banking/corporates/transfers
	@PostMapping("/banking/corporates/transfers")
	public TransfersModel getBankingCorpTransfer() throws IOException, IOException {
		String date = getDateNowV2();
		Corporates corp = new Corporates("BCAAPI2016", "0201245680", "00000001", date, "12345/PO/2016", "IDR",
				"100000.00", "0201245681", "Transfer Test", "Online Transfer");

		// buat url sakuku
		String urlSendbox = sendbox + bankCorpTrans;

		// buat jsonstring
		String jsonstr = convertJsonToString(corp);
	
		// buat token
		Token token = postToken();
		if (token != null) {
			// buat signature
			SignatureHeader signHeader = new SignatureHeader(bankCorpTrans, token.getAccess_token(), "POST", jsonstr);
			Signature signature = getSignature(signHeader);

			if (signature != null) {
				HttpResponse response = Request.Post(urlSendbox).addHeader("Content-Type", "application/json")
						.addHeader("X-BCA-KEY", bcaKey).addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac())
						.bodyString(jsonstr, ContentType.APPLICATION_JSON).execute().returnResponse();
				String stringEntity = convertStringLowrcase(EntityUtils.toString(response.getEntity()));
				
				ObjectMapper mapper = new ObjectMapper();
				TransfersModel result = mapper.readValue(stringEntity, TransfersModel.class);
				result.setStatus(result.getStatus().substring(0, 1).toUpperCase() + result.getStatus().substring(1));
				return result;

			}

		}
		return new TransfersModel();

	}

	// GET /banking/v3/corporates/{corporateId}/accounts/{accountNumbers}

	@GetMapping("/v3/corporates")
	public String getBankCorpsV3() throws ParseException, IOException {
		String CorporateID = "BCAAPI2016";
		String AccountNumbers = "0201245680";
		String corpsv3 = bankingV3Crops + "/corporates/" + CorporateID + "/accounts/" + AccountNumbers;
		String url = sendbox + corpsv3;
		String result = "";
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(corpsv3, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(url).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				result = EntityUtils.toString(response.getEntity());
			}

		}
		return result;

	}

	// GET /banking/v3/corporates/{corporateId}/accounts/{accountNumbers}/statements

	@GetMapping("/v3/corporates/statements")
	public String getBankCorpsV3statements() throws ParseException, IOException {
		String CorporateID = "BCAAPI2016";
		String AccountNumbers = "0201245680";
		String corpsv3 = bankingV3Crops + "/corporates/" + CorporateID + "/accounts/" + AccountNumbers
				+ "/statements?StartDate=2016-09-01&EndDate=2016-09-01";
		String url = sendbox + corpsv3;
		Token token = postToken();
		if (token != null) {
			SignatureHeader signHeader = new SignatureHeader(corpsv3, token.getAccess_token(), "GET", null);
			Signature signature = getSignature(signHeader);
			if (signature != null) {
				HttpResponse response = Request.Get(url).addHeader("X-BCA-KEY", bcaKey)
						.addHeader("Authorization", "Bearer " + token.getAccess_token())
						.addHeader("X-BCA-Timestamp", signature.getTimestamp())
						.addHeader("X-BCA-Signature", signature.getHmac()).execute().returnResponse();
				String stringEntity = convertStringLowrcase(EntityUtils.toString(response.getEntity()));
				System.out.println(stringEntity);
				/*
				 * ObjectMapper mapper = new ObjectMapper(); CorporatesStatementsModel result =
				 * mapper.readValue(stringEntity, CorporatesStatementsModel.class);
				 */

				return EntityUtils.toString(response.getEntity());
			}

		}
		return "";

	}

	public String getDateNowV2() {
		DateFormat dateFormatYear = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String timestamp = dateFormatYear.format(date).toString();
		return timestamp;
	}

	public String getDateNow() {
		DateFormat dateFormatYear = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormatHour = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String timestamp = dateFormatYear.format(date).toString() + "T" + dateFormatHour.format(date).toString()
				+ ".000+07:00";
		return timestamp;
	}

	public String convertJsonToString(Object object) {
		// Mapping json jadi string
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonstr = "";
		try {
			jsonstr = mapperObj.writeValueAsString(object);
			// Mapping Strin depan jadi uppercase
			jsonstr = convertStringUppercase(jsonstr);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jsonstr;
	}

	@SuppressWarnings("unchecked")
	public String convertStringLowrcase(String jsonconvert) {
		// ngebuat jadi uppercase depannya
		@SuppressWarnings("rawtypes")
		List<String> list = new ArrayList();
		for (int i = 0; i < jsonconvert.length(); i++) {
			if (i > jsonconvert.length() - 2) {
				list.add("\"");
				break;
			} else {
				String a = jsonconvert.substring(i, i + 1);
				if (a.equals("\"")) {
					list.add(a);
					i++;
					a = jsonconvert.substring(i, i + 1);
					list.add(a.toLowerCase());
				} else {
					list.add(a);
				}
			}

		}
		String jsonstr = "";
		for (String ls : list) {
			jsonstr = jsonstr + ls;
		}
		return jsonstr;
	}

	@SuppressWarnings("unchecked")
	public String convertStringUppercase(String jsonconvert) {
		// ngebuat jadi uppercase depannya
		@SuppressWarnings("rawtypes")
		List<String> list = new ArrayList();
		for (int i = 0; i < jsonconvert.length(); i++) {
			if (i > jsonconvert.length() - 2) {
				list.add("\"");
				break;
			} else {
				String a = jsonconvert.substring(i, i + 1);
				if (a.equals("\"")) {
					list.add(a);
					i++;
					a = jsonconvert.substring(i, i + 1);
					list.add(a.toUpperCase());
				} else {
					list.add(a);
				}
			}

		}
		String jsonstr = "";
		for (String ls : list) {
			jsonstr = jsonstr + ls;
		}
		return jsonstr;
	}

}
