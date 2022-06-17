package split.com.app.data.model.chat_sender;

public class SenderModel {
    private final String _message;
    private final String time;

    public SenderModel(String _message, String time) {
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
