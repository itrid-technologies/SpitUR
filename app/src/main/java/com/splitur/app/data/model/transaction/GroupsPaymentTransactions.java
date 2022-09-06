package com.splitur.app.data.model.transaction;

import com.google.gson.annotations.SerializedName;

public class GroupsPaymentTransactions{

	@SerializedName("group_title")
	private String groupTitle;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("sub_category")
	private SubCategory subCategory;

	public String getGroupTitle(){
		return groupTitle;
	}

	public int getGroupId(){
		return groupId;
	}

	public SubCategory getSubCategory(){
		return subCategory;
	}
}