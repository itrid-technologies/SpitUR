package com.splitur.app.data.model.chat_receiver;

import com.splitur.app.data.model.receive_message.GroupSender;

public class ReceiverModel {
    private String Message;
    private String time;
    private GroupSender receiver;


    public ReceiverModel(String message, String time, GroupSender receiver1) {
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

    public GroupSender getReceiver() {
        return receiver;
    }
}
