package com.splitur.app.data.model.get_avatar;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AvatarModel{

	@SerializedName("avatar")
	private List<AvatarItem> avatar;

	public List<AvatarItem> getAvatar(){
		return avatar;
	}
}

