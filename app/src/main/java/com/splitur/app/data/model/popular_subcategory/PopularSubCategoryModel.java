package com.splitur.app.data.model.popular_subcategory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PopularSubCategoryModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("page")
	private int page;

	public List<DataItem> getData(){
		return data;
	}

	public int getPage() {
		return page;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}