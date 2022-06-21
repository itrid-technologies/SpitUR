package split.com.app.ui.main.viewmodel.chat_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.model.receive_message.GetMessagesModel;
import split.com.app.data.model.send_message.MessageSendModel;
import split.com.app.data.repository.chat.ChatRepository;
import split.com.app.data.repository.join_group.JoinGroupRepository;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<MessageSendModel> data;
    private MutableLiveData<GetMessagesModel> messages_data;
    String id, msg;
    private ChatRepository chatRepository;



    public ChatViewModel(String receiver_id, String message) {
        this.id = receiver_id;
        this.msg = message;
        chatRepository = new ChatRepository();
    }

    public void initSendMessage() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = chatRepository.send(msg,id);
    }

    public void initGetAllMessage() {
        if (this.messages_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        messages_data = chatRepository.getMessages(id);
    }

    public MutableLiveData<MessageSendModel> getData() {
        return this.data;
    }

    public MutableLiveData<GetMessagesModel> getMessages_data() {
        return this.messages_data;
    }

}