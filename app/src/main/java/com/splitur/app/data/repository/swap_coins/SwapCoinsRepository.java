package com.splitur.app.data.repository.swap_coins;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class SwapCoinsRepository {
    private ApiService apiService;

    public SwapCoinsRepository() {

    }

    public MutableLiveData<BasicModel> swap(String coins) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("coins", coins);


        final MutableLiveData<BasicModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.swapCoins("Bearer " +token,object);
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
}
