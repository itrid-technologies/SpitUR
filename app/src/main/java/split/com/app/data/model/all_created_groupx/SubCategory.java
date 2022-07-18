package split.com.app.data.model.all_created_groupx;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategory{

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

	@SerializedName("plan")
	private List<PlanItem> plan;

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

	public List<PlanItem> getPlan(){
		return plan;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}