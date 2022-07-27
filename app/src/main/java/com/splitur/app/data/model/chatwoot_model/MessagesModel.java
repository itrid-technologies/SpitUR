package com.splitur.app.data.model.chatwoot_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MessagesModel{

	@SerializedName("payload")
	private List<PayloadItem> payload;

	@SerializedName("meta")
	private Meta meta;

	public List<PayloadItem> getPayload(){
		return payload;
	}

	public Meta getMeta(){
		return meta;
	}
}