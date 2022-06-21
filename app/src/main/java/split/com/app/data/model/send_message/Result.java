package split.com.app.data.model.send_message;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("receiver_id")
	private int receiverId;

	@SerializedName("id")
	private int id;

	@SerializedName("body")
	private String body;

	@SerializedName("sender_id")
	private int senderId;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getReceiverId(){
		return receiverId;
	}

	public int getId(){
		return id;
	}

	public String getBody(){
		return body;
	}

	public int getSenderId(){
		return senderId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}