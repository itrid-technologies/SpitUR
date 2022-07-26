package com.splitur.app.data.repository.split_score;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.GetScoreModel;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class ScoreRepository {
    private ApiService apiService;

    public ScoreRepository() {
    }

    public MutableLiveData<BasicModel> uploadScore(String groupId , String adminId , String score) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("group_id", groupId);
        object.addProperty("admin_id", adminId);
        object.addProperty("split_score", score);

        final MutableLiveData<BasicModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.sendScore("Bearer "+token,object);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }

    public MutableLiveData<GetScoreModel> getScore(String groupId) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<GetScoreModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GetScoreModel> call = apiService.getScore("Bearer "+token,groupId);
        call.enqueue(new Callback<GetScoreModel>() {
            @Override
            public void onResponse(@NonNull Call<GetScoreModel> call, @NonNull Response<GetScoreModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetScoreModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

}
