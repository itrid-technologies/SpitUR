package split.com.app.data.model.receive_message;

import com.google.gson.annotations.SerializedName;

public class MessagesItem{

	@SerializedName("createdAt")
	private String createdAt;


	@SerializedName("group_id")
	private int groupId;

	@SerializedName("group_sender")
	private GroupSender groupSender;

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


	public int getGroupId(){
		return groupId;
	}

	public GroupSender getGroupSender(){
		return groupSender;
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