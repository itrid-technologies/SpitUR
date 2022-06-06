package split.com.app.data.model.send_otp;

import com.google.gson.annotations.SerializedName;

public class OtpModel{

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