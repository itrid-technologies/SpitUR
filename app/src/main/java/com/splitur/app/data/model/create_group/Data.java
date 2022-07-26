package com.splitur.app.data.model.create_group;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("slots")
	private int slots;

	@SerializedName("password")
	private String password;

	@SerializedName("visibility")
	private boolean visibility;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("cost_per_member")
	private int costPerMember;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("plans_id")
	private int plansId;

	@SerializedName("email")
	private String email;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getSlots(){
		return slots;
	}

	public String getPassword(){
		return password;
	}

	public boolean isVisibility(){
		return visibility;
	}

	public int getGroupId(){
		return groupId;
	}

	public int getCostPerMember(){
		return costPerMember;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public int getPlansId(){
		return plansId;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}