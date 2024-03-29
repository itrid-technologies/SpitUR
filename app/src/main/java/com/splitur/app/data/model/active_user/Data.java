package com.splitur.app.data.model.active_user;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("earned_coins")
	private String earned_coins;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	@SerializedName("email")
	private String email;

	public String getEarned_coins() {
		return earned_coins;
	}

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

	public String getEmail(){
		return email;
	}
}