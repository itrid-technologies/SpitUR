package split.com.app.data.model.all_joined_groups;

import com.google.gson.annotations.SerializedName;

public class Group{

	@SerializedName("group_title")
	private String groupTitle;

	@SerializedName("visibility")
	private boolean visibility;

	@SerializedName("sub_category")
	private SubCategory subCategory;

	@SerializedName("group_admin")
	private GroupAdmin groupAdmin;

	@SerializedName("total_members")
	private String totalMembers;

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

	@SerializedName("plan_id")
	private String planId;

	@SerializedName("plans_id")
	private Object plansId;

	@SerializedName("status")
	private boolean status;

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

	public String getTotalMembers(){
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

	public String getPlanId(){
		return planId;
	}

	public Object getPlansId(){
		return plansId;
	}

	public boolean isStatus(){
		return status;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}