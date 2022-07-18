package split.com.app.data.model.getch_memeber_messages;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetMemberMessagesModel{

	@SerializedName("messages")
	private List<MessagesItem> messages;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<MessagesItem> getMessages(){
		return messages;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}