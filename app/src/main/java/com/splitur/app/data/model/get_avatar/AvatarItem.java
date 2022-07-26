package com.splitur.app.data.model.get_avatar;

import com.google.gson.annotations.SerializedName;

public class AvatarItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}