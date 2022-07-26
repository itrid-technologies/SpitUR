package com.splitur.app.data.repository.faq;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.faq.FaqListModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class FaqRepository {
    private ApiService apiService;

    public FaqRepository() {
    }

    public MutableLiveData<FaqListModel> getFaqList() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<FaqListModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<FaqListModel> call = apiService.faqList("Bearer "+token);
        call.enqueue(new Callback<FaqListModel>() {
            @Override
            public void onResponse(@NonNull Call<FaqListModel> call, @NonNull Response<FaqListModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<FaqListModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }
}
