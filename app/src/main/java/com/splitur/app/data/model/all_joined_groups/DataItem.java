package com.splitur.app.data.model.all_joined_groups;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("subscription_id")
	private String subscriptionId;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("enrollment_active")
	private boolean enrollmentActive;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("payment_status")
	private String paymentStatus;

	@SerializedName("id")
	private int id;

	@SerializedName("upi_id")
	private String upiId;

	@SerializedName("otp_requsted")
	private boolean otpRequsted;

	@SerializedName("updatedAt")
	private String updatedAt;

	@SerializedName("group")
	private Group group;

	public String getSubscriptionId(){
		return subscriptionId;
	}

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

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public int getId(){
		return id;
	}

	public String getUpiId(){
		return upiId;
	}

	public boolean isOtpRequsted(){
		return otpRequsted;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public Group getGroup(){
		return group;
	}
}