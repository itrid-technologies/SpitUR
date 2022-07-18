package split.com.app.data.model.getch_memeber_messages;

import com.google.gson.annotations.SerializedName;

public class Receiver{

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