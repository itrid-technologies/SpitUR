package split.com.app.data.model.receive_message;

import com.google.gson.annotations.SerializedName;

public class GroupSender{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("avatar")
	private String avatar;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getAvatar(){
		return avatar;
	}
}