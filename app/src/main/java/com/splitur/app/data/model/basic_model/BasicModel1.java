package com.splitur.app.data.model.basic_model;

import com.google.gson.annotations.SerializedName;

public class BasicModel1 {

	@SerializedName("message")
	private String message;

	@SerializedName("success")
	private String status;

	public String getMessage(){
		return message;
	}

	public String isStatus(){
		return status;
	}
}