package com.splitur.app.data.model;

public class OTpModel {
    private final String _message;
    private final String time;

    public OTpModel(String _message, String time) {
        this._message = _message;
        this.time = time;
    }

    public String get_message() {
        return _message;
    }

    public String getTime() {
        return time;
    }
}
