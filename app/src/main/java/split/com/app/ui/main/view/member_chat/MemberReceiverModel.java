package split.com.app.ui.main.view.member_chat;

import split.com.app.data.model.chat_receiver.ReceiverModel;
import split.com.app.data.model.getch_memeber_messages.Sender;
import split.com.app.data.model.receive_message.GroupSender;

public class MemberReceiverModel {
    private String Message;
    private String time;
    private Sender receiver;


    public MemberReceiverModel(String message, String time, Sender receiver1) {
        Message = message;
        this.time = time;
        this.receiver = receiver1;
    }

    public String getMessage() {
        return Message;
    }

    public String getTime() {
        return time;
    }

    public Sender getReceiver() {
        return receiver;
    }

}
