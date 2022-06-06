package split.com.app.data.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}