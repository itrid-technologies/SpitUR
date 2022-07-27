package com.splitur.app.ui.main.viewmodel.chatwoot_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.chatwoot_model.ConversationModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.data.model.chatwoot_model.SendSupportMessageModel;
import com.splitur.app.data.repository.chatwoot_repository.CreateConversationRepository;

public class SupportChatViewModel extends ViewModel {

    private MutableLiveData<ConversationModel> data;
    private MutableLiveData<MessagesModel> chat;
    private MutableLiveData<SendSupportMessageModel> send_data;

    private final CreateConversationRepository conversationRepository;
    int conversation_id;
    String message;



    public SupportChatViewModel(int data, String query) {
        this.conversation_id = data;
        this.message = query;
        conversationRepository = new CreateConversationRepository();
    }

    public void init() {
        if (this.data != null) {
            return;
        }
        data = conversationRepository.conversation();
    }

    public void getChat() {
        if (this.chat != null) {
            return;
        }
        chat = conversationRepository.getMessages();
    }

    public void initQuery() {
        if (this.send_data != null) {
            return;
        }
        send_data = conversationRepository.sendQuery(conversation_id,message);
    }

    public MutableLiveData<ConversationModel> getData() {
        return this.data;
    }

    public MutableLiveData<MessagesModel> getChatData() {
        return this.chat;
    }

    public MutableLiveData<SendSupportMessageModel> getSend_data() {
        return this.send_data;
    }



}
