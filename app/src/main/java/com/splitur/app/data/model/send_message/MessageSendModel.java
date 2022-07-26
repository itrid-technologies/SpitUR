package com.splitur.app.data.model.send_message;

import com.google.gson.annotations.SerializedName;

public class MessageSendModel{

	@SerializedName("result")
	private Result result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public Result getResult(){
		return result;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}