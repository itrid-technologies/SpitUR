package com.splitur.app.data.model.group_detail;

import com.google.gson.annotations.SerializedName;

public class PlanItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("color")
	private String color;

	@SerializedName("sub_category_id")
	private int subCategoryId;

	@SerializedName("name")
	private String name;

	@SerializedName("icon")
	private String icon;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getColor(){
		return color;
	}

	public int getSubCategoryId(){
		return subCategoryId;
	}

	public String getName(){
		return name;
	}

	public String getIcon(){
		return icon;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}