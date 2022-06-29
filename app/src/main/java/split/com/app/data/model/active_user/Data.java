package split.com.app.data.model.active_user;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("userId")
	private String userId;

	@SerializedName("email")
	private String email;

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getAvatar(){
		return avatar;
	}

	public String getUserId(){
		return userId;
	}

	public String getEmail(){
		return email;
	}
}