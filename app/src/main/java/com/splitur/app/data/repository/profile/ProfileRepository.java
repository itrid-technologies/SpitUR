package com.splitur.app.data.repository.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.active_user.ActiveUserModel;
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.data.model.total_coins.TotalCoinsModel;
import com.splitur.app.data.model.update_user_profile.UserUpdateModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class ProfileRepository {
    private ApiService apiService;

    public ProfileRepository() {

    }

    public MutableLiveData<UserUpdateModel> upDateProfile(String name, String id, String avatar,String email) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("avatar", avatar);
        object.addProperty("userId", id);
        object.addProperty("email", email);


        final MutableLiveData<UserUpdateModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<UserUpdateModel> call = apiService.updateProfile("Bearer " +token, object);
        call.enqueue(new Callback<UserUpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UserUpdateModel> call, @NonNull Response<UserUpdateModel> response) {
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
            public void onFailure(@NonNull Call<UserUpdateModel> call, @NonNull Throwable t) {
                Log.e("Update Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<TotalCoinsModel> getTotalCoins(String id) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");



        final MutableLiveData<TotalCoinsModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<TotalCoinsModel> call = apiService.getTotalCoins("Bearer " +token, id);
        call.enqueue(new Callback<TotalCoinsModel>() {
            @Override
            public void onResponse(@NonNull Call<TotalCoinsModel> call, @NonNull Response<TotalCoinsModel> response) {
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
            public void onFailure(@NonNull Call<TotalCoinsModel> call, @NonNull Throwable t) {
                Log.e("Update Error",t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<ActiveUserModel> getUser() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");



        final MutableLiveData<ActiveUserModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<ActiveUserModel> call = apiService.getUserData("Bearer " +token);
        call.enqueue(new Callback<ActiveUserModel>() {
            @Override
            public void onResponse(@NonNull Call<ActiveUserModel> call, @NonNull Response<ActiveUserModel> response) {
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
            public void onFailure(@NonNull Call<ActiveUserModel> call, @NonNull Throwable t) {
                Log.e("Update Error",t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<BasicModel1> logout(String id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");



        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.logoutUser("Bearer " +token,id);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if(response.body()!=null)
                {
                    preferences.clearData();
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
                Log.e("Update Error",t.getMessage());
            }
        });

        return liveData;
    }
}
