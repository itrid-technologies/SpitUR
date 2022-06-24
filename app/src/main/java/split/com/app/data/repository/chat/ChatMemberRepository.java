package split.com.app.data.repository.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.getch_memeber_messages.GetMemberMessagesModel;
import split.com.app.data.model.receive_message.GetMessagesModel;
import split.com.app.data.model.send_message.MessageSendModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class ChatMemberRepository {
    private ApiService apiService;

    public ChatMemberRepository() {
    }

    public MutableLiveData<MessageSendModel> send(String message , String receiver_id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("body", message);
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageSendModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GetMemberMessagesModel> getMessages(String id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GetMemberMessagesModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GetMemberMessagesModel> call = apiService.getMessages("Bearer "+token,id);
        call.enqueue(new Callback<GetMemberMessagesModel>() {
            @Override
            public void onResponse(@NonNull Call<GetMemberMessagesModel> call, @NonNull Response<GetMemberMessagesModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
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
