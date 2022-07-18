package split.com.app.data.repository.friends;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.faq.FaqListModel;
import split.com.app.data.model.friend_list.FriendListModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class FriendsRepository {
    private ApiService apiService;

    public FriendsRepository() {
    }

    public MutableLiveData<FriendListModel> getFriendList(String data) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<FriendListModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<FriendListModel> call = apiService.friendList("Bearer "+token, data);
        call.enqueue(new Callback<FriendListModel>() {
            @Override
            public void onResponse(@NonNull Call<FriendListModel> call, @NonNull Response<FriendListModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<FriendListModel> call, @NonNull Throwable t) {
                Log.e("Friend List Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }
}
