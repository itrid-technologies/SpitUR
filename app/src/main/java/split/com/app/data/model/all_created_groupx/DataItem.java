package split.com.app.data.model.all_created_groupx;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("group_title")
	private String groupTitle;

	@SerializedName("visibility")
	private boolean visibility;

	@SerializedName("sub_category")
	private SubCategory subCategory;

	@SerializedName("group_admin")
	private GroupAdmin groupAdmin;

	@SerializedName("total_members")
	private int totalMembers;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("slots")
	private int slots;

	@SerializedName("password")
	private String password;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("sub_category_id")
	private int subCategoryId;

	@SerializedName("cost_per_member")
	private int costPerMember;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("plans_id")
	private int plansId;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getGroupTitle(){
		return groupTitle;
	}

	public boolean isVisibility(){
		return visibility;
	}

	public SubCategory getSubCategory(){
		return subCategory;
	}

	public GroupAdmin getGroupAdmin(){
		return groupAdmin;
	}

	public int getTotalMembers(){
		return totalMembers;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getSlots(){
		return slots;
	}

	public String getPassword(){
		return password;
	}

	public int getGroupId(){
		return groupId;
	}

	public int getUserId(){
		return userId;
	}

	public int getSubCategoryId(){
		return subCategoryId;
	}

	public int getCostPerMember(){
		return costPerMember;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public int getPlansId(){
		return plansId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}