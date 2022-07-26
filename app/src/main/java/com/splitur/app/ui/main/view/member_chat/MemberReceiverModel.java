package com.splitur.app.ui.main.view.member_chat;

import com.splitur.app.data.model.getch_memeber_messages.Sender;

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
