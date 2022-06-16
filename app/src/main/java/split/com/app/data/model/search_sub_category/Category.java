package split.com.app.data.model.search_sub_category;

import com.google.gson.annotations.SerializedName;

public class Category{

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private int id;

	public String getIcon(){
		return icon;
	}

	public int getId(){
		return id;
	}
}