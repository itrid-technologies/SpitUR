package split.com.app.data.model.getch_memeber_messages;

import com.google.gson.annotations.SerializedName;

public class MessagesItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("receiver")
	private Receiver receiver;

	@SerializedName("sender")
	private Sender sender;

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

	public Receiver getReceiver(){
		return receiver;
	}

	public Sender getSender(){
		return sender;
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