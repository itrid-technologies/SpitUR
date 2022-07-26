package com.splitur.app.data.model.phone_number;

import com.google.gson.annotations.SerializedName;

public class NumberModel{

	@SerializedName("phone")
	private String phone;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getPhone(){
		return phone;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}