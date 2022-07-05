package split.com.app.data.model;

import com.google.gson.annotations.SerializedName;

public class SubCategory{

	@SerializedName("sub_cat_title")
	private String subCatTitle;

	@SerializedName("category")
	private Category category;

	public String getSubCatTitle(){
		return subCatTitle;
	}

	public Category getCategory(){
		return category;
	}
}