package com.splitur.app.data.repository.created_and_joined;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.all_created_groupx.AllCreatedGroupModel;
import com.splitur.app.data.model.all_joined_groups.AllJoinedGroupModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class CreatedAndJoinedGroupRepository {
    private ApiService apiService;

    public CreatedAndJoinedGroupRepository() {
    }

    public MutableLiveData<AllCreatedGroupModel> getAllCreatedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<AllCreatedGroupModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<AllCreatedGroupModel> call = apiService.getCreatedGroups("Bearer "+token);
        call.enqueue(new Callback<AllCreatedGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<AllCreatedGroupModel> call, @NonNull Response<AllCreatedGroupModel> response) {
                if(response.body()!=null)
                {
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
            public void onFailure(@NonNull Call<AllCreatedGroupModel> call, @NonNull Throwable t) {
                Log.e("Created Groups Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<AllJoinedGroupModel> getAllJoinedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<AllJoinedGroupModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<AllJoinedGroupModel> call = apiService.getJoinedGroups("Bearer "+token);
        call.enqueue(new Callback<AllJoinedGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<AllJoinedGroupModel> call, @NonNull Response<AllJoinedGroupModel> response) {
                if(response.body()!=null)
                {
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
            public void onFailure(@NonNull Call<AllJoinedGroupModel> call, @NonNull Throwable t) {
                Log.e("Joined Groups Error",t.getMessage());
            }
        });

        return liveData;
    }
}
