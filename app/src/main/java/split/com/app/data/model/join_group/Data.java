package split.com.app.data.model.join_group;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("password")
	private String password;

	@SerializedName("group_id")
	private int groupId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("email")
	private String email;

	public String getPassword(){
		return password;
	}

	public int getGroupId(){
		return groupId;
	}

	public int getUserId(){
		return userId;
	}

	public String getEmail(){
		return email;
	}
}