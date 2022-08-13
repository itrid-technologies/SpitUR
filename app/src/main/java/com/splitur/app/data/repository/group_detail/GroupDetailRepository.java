package com.splitur.app.data.repository.group_detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.group_detail.GroupDetailModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailRepository {
    private ApiService apiService;

    public GroupDetailRepository() {
    }

    public MutableLiveData<GroupDetailModel> getDetails(String id, String page_no) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("page", page_no);

        Map<String, String> parms = new HashMap<String, String>();
        parms.put("sub_cat_id", id);


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetails("Bearer " + token, object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearch(String id, String query, int page) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("query", query);
        object.addProperty("page", String.valueOf(page));


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch("Bearer " + token, object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearchGroupId(String id, String groupId, int page) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("group_id", groupId);
        object.addProperty("page", String.valueOf(page));


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch("Bearer " + token, object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailsBySearchUserId(String id, String groupId, int page) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("sub_cat_id", id);
        object.addProperty("user_id", groupId);
        object.addProperty("page", String.valueOf(page));


        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getGroupDetailsSearch("Bearer " + token, object);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

}
