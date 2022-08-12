package com.splitur.app.ui.main.view.support_chat;

import com.splitur.app.data.model.chatwoot_model.Assignee;
import com.splitur.app.data.model.receive_message.GroupSender;

public class SupportReceiverModel {
    private String Message;
    private int time;
    private Assignee receiver;


    public SupportReceiverModel(String message, int time, Assignee receiver1) {
        Message = message;
        this.time = time;
        this.receiver = receiver1;
    }

    public String getMessage() {
        return Message;
    }

    public int getTime() {
        return time;
    }

    public Assignee getReceiver() {
        return receiver;
    }
}
