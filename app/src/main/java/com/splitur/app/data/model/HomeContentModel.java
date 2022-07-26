package com.splitur.app.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeContentModel{

	@SerializedName("data")
	private List<HomeDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<HomeDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}