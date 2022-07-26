package com.splitur.app.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeDataItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("sub_category")
	private List<SubCategoryItem> subCategory;

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

	public List<SubCategoryItem> getSubCategory(){
		return subCategory;
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