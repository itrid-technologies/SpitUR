package com.splitur.app.data.repository.ONLINE_OFFLINE;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class UserOnlineStatusRepository {
    private ApiService apiService;

    public UserOnlineStatusRepository() {
    }

    public MutableLiveData<BasicModel1> userStatus(int status) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.setUserOnlineStatus("Bearer " + token ,status);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return liveData;
    }

}
