package com.splitur.app.data.model;

import com.google.gson.annotations.SerializedName;

public class ChatWootAccountIdModel{

	@SerializedName("chat_woot_account_id")
	private String chatWootAccountId;

	@SerializedName("inbox_id")
	private String inbox_id;

	@SerializedName("chat_api_key")
	private String chat_api_key;

	public String getChatWootAccountId() {
		return chatWootAccountId;
	}

	public String getInbox_id() {
		return inbox_id;
	}

	public String getChat_api_key() {
		return chat_api_key;
	}
}