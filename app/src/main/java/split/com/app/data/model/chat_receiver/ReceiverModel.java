package split.com.app.data.model.chat_receiver;

public class ReceiverModel {
    private String Message;
    private String time;

    public ReceiverModel(String message, String time) {
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
