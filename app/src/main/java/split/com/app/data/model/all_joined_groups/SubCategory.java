package split.com.app.data.model.all_joined_groups;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategory{

	@SerializedName("validation_type")
	private String validationType;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("is_popular")
	private boolean isPopular;

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

	public String getValidationType(){
		return validationType;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public boolean isIsPopular(){
		return isPopular;
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