package split.com.app.data.model.all_joined_groups;

import com.google.gson.annotations.SerializedName;

public class GroupAdmin{

	@SerializedName("coins")
	private int coins;

	@SerializedName("wallet_balance")
	private Double walletBalance;

	@SerializedName("splitur_score")
	private int spliturScore;

	@SerializedName("otpHash")
	private String otpHash;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	@SerializedName("online_ofline_status")
	private boolean onlineOflineStatus;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("fcm_token")
	private String fcmToken;

	@SerializedName("id")
	private int id;

	@SerializedName("last_active")
	private String lastActive;

	@SerializedName("email")
	private String email;

	@SerializedName("updatedAt")
	private String updatedAt;

	public int getCoins(){
		return coins;
	}

	public Double getWalletBalance(){
		return walletBalance;
	}

	public int getSpliturScore(){
		return spliturScore;
	}

	public String getOtpHash(){
		return otpHash;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getUserId(){
		return userId;
	}

	public boolean isOnlineOflineStatus(){
		return onlineOflineStatus;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public int getId(){
		return id;
	}

	public String getLastActive(){
		return lastActive;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}