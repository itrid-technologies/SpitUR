package com.splitur.app.data.model.search_sub_category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchSubCatModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<DataItem> getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}