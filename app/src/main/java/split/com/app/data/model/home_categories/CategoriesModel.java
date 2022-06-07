package split.com.app.data.model.home_categories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel{

	@SerializedName("data")
	private List<CategoryDataItems> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<CategoryDataItems> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}