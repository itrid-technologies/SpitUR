package com.splitur.app.data.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("amount")
	private int amount;

	@SerializedName("payment_type")
	private String paymentType;

	@SerializedName("payment_status")
	private String paymentStatus;

	@SerializedName("groups_payment_transactions")
	private GroupsPaymentTransactions groupsPaymentTransactions;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getAmount(){
		return amount;
	}

	public String getPaymentType(){
		return paymentType;
	}

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public GroupsPaymentTransactions getGroupsPaymentTransactions(){
		return groupsPaymentTransactions;
	}
}