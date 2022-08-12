package com.splitur.app.data.model.friend_list;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getUserId(){
		return userId;
	}
}