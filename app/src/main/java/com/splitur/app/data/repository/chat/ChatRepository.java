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
import com.splitur.app.data.model.receive_message.GetMessagesModel;
import com.splitur.app.data.model.send_message.MessageSendModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class ChatRepository {
    private ApiService apiService;

    public ChatRepository() {
    }

    public MutableLiveData<MessageSendModel> send(String message , String group_id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("body", message);
        object.addProperty("group_id",group_id);

        final MutableLiveData<MessageSendModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        try {


        Call<MessageSendModel> call = apiService.sendGroupMessage("Bearer "+token,object);
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

        }catch (IllegalStateException e){
            Log.e("",e.getMessage());
        }

        return liveData;
    }

    public MutableLiveData<GetMessagesModel> getMessages(String id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GetMessagesModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GetMessagesModel> call = apiService.getGroupMessages("Bearer "+token,id);
        call.enqueue(new Callback<GetMessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<GetMessagesModel> call, @NonNull Response<GetMessagesModel> response) {
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
            public void onFailure(@NonNull Call<GetMessagesModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }


}
