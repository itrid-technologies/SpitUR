package com.splitur.app.data.model.join_group;

import com.google.gson.annotations.SerializedName;

public class JoinGroupModel{

	@SerializedName("subscriptions")
	private Subscriptions subscriptions;

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public Subscriptions getSubscriptions(){
		return subscriptions;
	}

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}