package split.com.app.data.model.group_member;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("enrollment_active")
	private boolean enrollmentActive;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	@SerializedName("otp_requsted")
	private Object otpRequsted;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public boolean isEnrollmentActive(){
		return enrollmentActive;
	}

	public int getUserId(){
		return userId;
	}

	public int getGroupId(){
		return groupId;
	}

	public int getId(){
		return id;
	}

	public User getUser(){
		return user;
	}

	public Object getOtpRequsted(){
		return otpRequsted;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}