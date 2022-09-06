package com.splitur.app.ui.main.view.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.notification.NotificationModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {

    private ApiService apiService;

    public NotificationRepository() {

    }

    public MutableLiveData<NotificationModel> getNotificationData(int page) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<NotificationModel> NotificationModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<NotificationModel> call = apiService.getNotifications("Bearer " + token,page);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {
                if (response.body() != null) {
                    NotificationModelMutableLiveData.setValue(response.body());
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(), response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(), response.errorBody());

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return NotificationModelMutableLiveData;
    }

    public MutableLiveData<NotificationCountModel> getNotificationCountData() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<NotificationCountModel> NotificationModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<NotificationCountModel> call = apiService.getNotificationCount("Bearer " + token);
        call.enqueue(new Callback<NotificationCountModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationCountModel> call, @NonNull Response<NotificationCountModel> response) {
                if (response.body() != null) {
                    NotificationModelMutableLiveData.setValue(response.body());
                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(), response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError(Split.getAppContext(), response.errorBody());

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NotificationCountModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return NotificationModelMutableLiveData;
    }

}
