package com.splitur.app.data.model.notification;

import com.google.gson.annotations.SerializedName;
import com.splitur.app.data.model.UserNotification;

public class NotificationDataItem{

	@SerializedName("type_message")
	private String typeMessage;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("user_notification")
	private UserNotification userNotification;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("type")
	private String type;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getTypeMessage(){
		return typeMessage;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getUserId(){
		return userId;
	}

	public String getGroupId(){
		return groupId;
	}

	public UserNotification getUserNotification(){
		return userNotification;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getBody(){
		return body;
	}

	public String getType(){
		return type;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}