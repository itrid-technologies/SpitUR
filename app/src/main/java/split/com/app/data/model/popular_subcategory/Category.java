package split.com.app.data.model.popular_subcategory;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public String getIcon(){
		return icon;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}