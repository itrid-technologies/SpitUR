package com.splitur.app.data.repository.chatwoot_repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.api.ChatApiService;
import com.splitur.app.data.api.ChatwootApiManager;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.model.chatwoot_model.ConversationModel;
import com.splitur.app.data.model.chatwoot_model.MessagesModel;
import com.splitur.app.data.model.chatwoot_model.SendSupportMessageModel;
import com.splitur.app.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateConversationRepository {

    private ChatApiService apiService;

    public CreateConversationRepository() {
    }

    public MutableLiveData<ConversationModel> conversation() {
        final MutableLiveData<ConversationModel> conversationModelMutableLiveData = new MutableLiveData<>();
        apiService = ChatwootApiManager.getRestApiService();

//        String api_key = "H5nSYfTLHHCU3YSTgE88pc8V";

        String source = Constants.SourceId;
        int inbox = Constants.InboxId;
        int contact = Constants.ContactId;
        String api_key = Constants.ChatApiKey;

        JsonObject object = new JsonObject();
        object.addProperty("source_id", source);
        object.addProperty("inbox_id",inbox);
        object.addProperty("contact_id",contact);

//        JsonObject object = new JsonObject();
//        object.addProperty("source_id", "789a935b-67d3-44b7-b203-492af2b0e2e6");
//        object.addProperty("inbox_id",17399);
//        object.addProperty("contact_id",22227579);


        Call<ConversationModel> call = apiService.createConversation(api_key,object);
        call.enqueue(new Callback<ConversationModel>() {
            @Override
            public void onResponse(@NonNull Call<ConversationModel> call, @NonNull Response<ConversationModel> response) {
                if (response.body() != null) {
                    conversationModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConversationModel> call, @NonNull Throwable t) {
                Log.e("Conversation Error", t.getMessage());
            }
        });

        return conversationModelMutableLiveData;
    }



    public MutableLiveData<MessagesModel> getMessages() {

        final MutableLiveData<MessagesModel> messagesModelMutableLiveData = new MutableLiveData<>();
        apiService = ChatwootApiManager.getRestApiService();

//        String api_key = "H5nSYfTLHHCU3YSTgE88pc8V";
        String api_key = Constants.ChatApiKey;


        Call<MessagesModel> call = apiService.getSupportChat(api_key);
        call.enqueue(new Callback<MessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<MessagesModel> call, @NonNull Response<MessagesModel> response) {
                if (response.body() != null) {
                    messagesModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessagesModel> call, @NonNull Throwable t) {
                Log.e("Support Chat Error", t.getMessage());
            }
        });

        return messagesModelMutableLiveData;

    }



    public MutableLiveData<SendSupportMessageModel> sendQuery(int conversation_id, String message) {

        final MutableLiveData<SendSupportMessageModel> liveData = new MutableLiveData<>();
        apiService = ChatwootApiManager.getRestApiService();

//        String api_key = "H5nSYfTLHHCU3YSTgE88pc8V";

        String api_key = Constants.ChatApiKey;


        JsonObject object = new JsonObject();
        object.addProperty("content", message);
        object.addProperty("message_type","outgoing");
        object.addProperty("private",true);

        Call<SendSupportMessageModel> call = apiService.sendSupportQuery(api_key,conversation_id,object);
        call.enqueue(new Callback<SendSupportMessageModel>() {
            @Override
            public void onResponse(@NonNull Call<SendSupportMessageModel> call, @NonNull Response<SendSupportMessageModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendSupportMessageModel> call, @NonNull Throwable t) {
                Log.e("Support Chat Error", t.getMessage());
            }
        });

        return liveData;

    }
}
