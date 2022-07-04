package split.com.app.data.model.all_joined_groups;

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

	@SerializedName("upi_id")
	private String upi_id;

	@SerializedName("subscription_id")
	private String subscription_id;

	@SerializedName("payment_status")
	private String payment_status;

	@SerializedName("otp_requsted")
	private boolean otpRequsted;

	@SerializedName("updatedAt")
	private String updatedAt;

	@SerializedName("group")
	private Group group;

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

	public boolean getOtpRequsted(){
		return otpRequsted;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public Group getGroup(){
		return group;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public boolean isOtpRequsted() {
		return otpRequsted;
	}

	public String getSubscription_id() {
		return subscription_id;
	}

	public String getUpi_id() {
		return upi_id;
	}
}