package com.splitur.app.data.model.otp_verification;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("otpHash")
	private String otpHash;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
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

	public String getOtpHash(){
		return otpHash;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getUserId(){
		return userId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}