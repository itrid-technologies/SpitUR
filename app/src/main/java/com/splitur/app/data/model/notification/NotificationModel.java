package com.splitur.app.data.model.notification;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationModel{

	@SerializedName("data")
	private List<NotificationDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("page")
	private int page;

	public List<NotificationDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public int getPage() {
		return page;
	}
}