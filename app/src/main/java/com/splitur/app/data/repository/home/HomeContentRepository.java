package com.splitur.app.data.repository.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.HomeContentModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class HomeContentRepository {

    private ApiService apiService;

    public HomeContentRepository() {
    }

    public MutableLiveData<HomeContentModel> getHomeContent() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<HomeContentModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<HomeContentModel> call = apiService.getHomeData(token);
        call.enqueue(new Callback<HomeContentModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeContentModel> call, @NonNull Response<HomeContentModel> response) {
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
            public void onFailure(@NonNull Call<HomeContentModel> call, @NonNull Throwable t) {
                Log.e("Category Error",t.getMessage());
            }
        });

        return liveData;
    }
}
