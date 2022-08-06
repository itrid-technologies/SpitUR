package com.splitur.app.data.model.otp_request;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("user_id")
	private String userId;

	public String getGroupId(){
		return groupId;
	}

	public String getUserId(){
		return userId;
	}
}