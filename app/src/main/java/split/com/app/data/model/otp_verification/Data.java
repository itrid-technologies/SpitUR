package split.com.app.data.model.otp_verification;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("created")
	private String created;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("otpHash")
	private String otpHash;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	@SerializedName("updated")
	private Object updated;

	@SerializedName("updatedAt")
	private String updatedAt;

	@SerializedName("token")
	private String token;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPhone(){
		return phone;
	}

	public String getCreated(){
		return created;
	}

	public String getName(){
		return name;
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

	public String getUserId(){
		return userId;
	}

	public Object getUpdated(){
		return updated;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getToken(){
		return token;
	}
}