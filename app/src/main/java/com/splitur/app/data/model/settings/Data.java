package com.splitur.app.data.model.settings;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("commission_percentage")
	private String commissionPercentage;

	@SerializedName("payment_fee")
	private String paymentFee;

	@SerializedName("privay_url")
	private String privayUrl;

	@SerializedName("base_url")
	private String baseUrl;

	@SerializedName("id")
	private int id;

	@SerializedName("terms_and_conditions_url")
	private String termsAndConditionsUrl;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getpaymentFee(){
		return paymentFee;
	}
	public String getCommissionPercentage(){
		return commissionPercentage;
	}

	public String getPrivayUrl(){
		return privayUrl;
	}

	public String getBaseUrl(){
		return baseUrl;
	}

	public int getId(){
		return id;
	}

	public String getTermsAndConditionsUrl(){
		return termsAndConditionsUrl;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}