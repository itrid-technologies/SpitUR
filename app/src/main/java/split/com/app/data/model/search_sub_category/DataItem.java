package split.com.app.data.model.search_sub_category;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("sub_cat_title")
	private String subCatTitle;

	@SerializedName("id")
	private int id;

	@SerializedName("category")
	private Category category;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public String getSubCatTitle(){
		return subCatTitle;
	}

	public int getId(){
		return id;
	}

	public Category getCategory(){
		return category;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}