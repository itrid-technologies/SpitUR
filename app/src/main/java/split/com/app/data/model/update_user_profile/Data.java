package split.com.app.data.model.update_user_profile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("splitur_score")
	private Object spliturScore;

	@SerializedName("id")
	private int id;

	@SerializedName("otpHash")
	private String otpHash;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("last_active")
	private String lastActive;

	@SerializedName("userId")
	private String userId;

	@SerializedName("online_ofline_status")
	private boolean onlineOflineStatus;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public Object getSpliturScore(){
		return spliturScore;
	}

	public int getId(){
		return id;
	}

	public String getOtpHash(){
		return otpHash;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getLastActive(){
		return lastActive;
	}

	public String getUserId(){
		return userId;
	}

	public boolean isOnlineOflineStatus(){
		return onlineOflineStatus;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}