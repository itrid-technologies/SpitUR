package split.com.app.data.model.basic_model;

import com.google.gson.annotations.SerializedName;

public class BasicModel {

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}