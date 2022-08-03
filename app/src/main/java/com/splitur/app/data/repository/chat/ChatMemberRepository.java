package com.splitur.app.data.repository.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.getch_memeber_messages.GetMemberMessagesModel;
import com.splitur.app.data.model.send_message.MessageSendModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class ChatMemberRepository {
    private ApiService apiService;

    public ChatMemberRepository() {
    }

    public MutableLiveData<MessageSendModel> send(String message ,String group_id, String receiver_id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("body", message);
        object.addProperty("group_id",group_id);
        object.addProperty("receiver_id",receiver_id);

        final MutableLiveData<MessageSendModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<MessageSendModel> call = apiService.sendMessage("Bearer "+token,object);
        call.enqueue(new Callback<MessageSendModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageSendModel> call, @NonNull Response<MessageSendModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<MessageSendModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GetMemberMessagesModel> getMessages(String receiver_id, String group_id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GetMemberMessagesModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();

        JsonObject object = new JsonObject();
        object.addProperty("group_id",group_id);
        object.addProperty("receiver_id",receiver_id);

        Call<GetMemberMessagesModel> call = apiService.getMessages("Bearer "+token,object);
        call.enqueue(new Callback<GetMemberMessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<GetMemberMessagesModel> call, @NonNull Response<GetMemberMessagesModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(),response.errorBody());

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetMemberMessagesModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }


}
