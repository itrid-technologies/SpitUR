package com.splitur.app.data.repository.friends;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.friend_list.FriendListModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class FriendsRepository {
    private ApiService apiService;

    public FriendsRepository() {
    }

    public MutableLiveData<FriendListModel> getFriendList() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<FriendListModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<FriendListModel> call = apiService.friendList("Bearer "+token);
        call.enqueue(new Callback<FriendListModel>() {
            @Override
            public void onResponse(@NonNull Call<FriendListModel> call, @NonNull Response<FriendListModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<FriendListModel> call, @NonNull Throwable t) {
                Log.e("Friend List Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }
}
