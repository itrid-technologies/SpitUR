package com.splitur.app.data.repository.referrel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class ReferralRepository {
    private ApiService apiService;

    public ReferralRepository() {
    }

    public MutableLiveData<BasicModel1> sendReferral(String refer_by, String referral) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        JsonObject object = new JsonObject();
        object.addProperty("referral", referral);
        object.addProperty("refer_by", refer_by);

        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.sendReferral("Bearer "+token,object);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if (response.body() != null) {
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
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return liveData;
    }
}
