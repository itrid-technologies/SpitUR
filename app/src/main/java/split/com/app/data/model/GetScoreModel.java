package split.com.app.data.model;

import com.google.gson.annotations.SerializedName;

public class GetScoreModel {

	@SerializedName("success")
	private boolean success;

	@SerializedName("split_score")
	private int splitScore;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public int getSplitScore(){
		return splitScore;
	}

	public String getMessage(){
		return message;
	}
}