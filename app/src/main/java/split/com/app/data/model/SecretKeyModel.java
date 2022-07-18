package split.com.app.data.model;

import com.google.gson.annotations.SerializedName;

public class SecretKeyModel{

	@SerializedName("message")
	private String message;

	@SerializedName("key")
	private String key;

	@SerializedName("status")
	private boolean status;

	public String getMessage(){
		return message;
	}

	public String getKey(){
		return key;
	}

	public boolean isStatus(){
		return status;
	}
}