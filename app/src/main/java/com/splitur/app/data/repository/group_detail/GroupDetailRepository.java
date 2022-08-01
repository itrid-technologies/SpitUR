package com.splitur.app.data.repository.group_detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.group_detail.GroupDetailModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class GroupDetailRepository {
    private ApiService apiService;

    public GroupDetailRepository() {
    }

    public MutableLiveData<GroupDetailModel> getDetails(String id) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);

        Map<String, String> parms = new HashMap<String, String>();
        parms.put("sub_cat_id", id);


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetails(object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearch(String id, String query) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("query", query);


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch(object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearchGroupId(String id, String groupId) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("group_id", groupId);


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch(object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearchUserId(String id, String groupId) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("user_id", groupId);


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch(object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

}
