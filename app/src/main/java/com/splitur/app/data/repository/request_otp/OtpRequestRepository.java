package com.splitur.app.data.repository.request_otp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class OtpRequestRepository {
    private ApiService apiService;

    public OtpRequestRepository() {
    }

    public MutableLiveData<BasicModel> request(String groupId) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");



        final MutableLiveData<BasicModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.request_otp("Bearer "+token,groupId);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }
}
