package com.splitur.app.data.model.join_group;

import com.google.gson.annotations.SerializedName;

public class SubCategory{

	@SerializedName("validation_type")
	private String validationType;

	public String getValidationType(){
		return validationType;
	}
}