package com.splitur.app.data.model.all_created_groupx;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getIcon(){
		return icon;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}