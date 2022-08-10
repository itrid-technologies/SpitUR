package com.splitur.app.data.model;

import com.splitur.app.data.model.chatwoot_model.Assignee;

public class SupportActionModel {
    private String Message;
    private String time;


    public SupportActionModel(String message, String time) {
        Message = message;
        this.time = time;
    }

    public String getMessage() {
        return Message;
    }

    public String getTime() {
        return time;
    }

}
