package com.splitur.app.data.repository.update_group;

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
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class UpdateGroupRepository {
    private ApiService apiService;

    public UpdateGroupRepository() {
    }

    public MutableLiveData<BasicModel1> updateEmail(String group_id, String email) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("email", email);

        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.updateGroup("Bearer "+token , group_id, object);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<BasicModel1> updatePass(String group_id, String pass) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("password", pass);

        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.updateGroup("Bearer "+token , group_id, object);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<BasicModel> updateVisibility(String group_id, boolean visible) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("visibility", visible);

        final MutableLiveData<BasicModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.updateGroupVisibility("Bearer "+token , group_id, object);
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
