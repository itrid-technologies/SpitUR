package split.com.app.data.model.faq;

import com.google.gson.annotations.SerializedName;

 public class DataItem {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("question")
	private String question;

	@SerializedName("answer")
	private String answer;

	@SerializedName("id")
	private int id;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getQuestion(){
		return question;
	}

	public String getAnswer(){
		return answer;
	}

	public int getId(){
		return id;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}