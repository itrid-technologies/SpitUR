package split.com.app.data.model.popular_subcategory;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

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

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public Category getCategory(){
		return category;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}