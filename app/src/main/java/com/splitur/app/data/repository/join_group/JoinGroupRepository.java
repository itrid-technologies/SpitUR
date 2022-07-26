package com.splitur.app.data.repository.join_group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.join_group.JoinGroupModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class JoinGroupRepository {
    private ApiService apiService;

    public JoinGroupRepository() {
    }

    public MutableLiveData<JoinGroupModel> join(String groupId, String email, String promoCode) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        JsonObject object = new JsonObject();
        object.addProperty("group_id", groupId);
        object.addProperty("email", email);
        object.addProperty("promoCode", promoCode);

        final MutableLiveData<JoinGroupModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<JoinGroupModel> call = apiService.joinGroup("Bearer " + token, object);
        call.enqueue(new Callback<JoinGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<JoinGroupModel> call, @NonNull Response<JoinGroupModel> response) {
                if (response.body() != null) {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JoinGroupModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }

}
