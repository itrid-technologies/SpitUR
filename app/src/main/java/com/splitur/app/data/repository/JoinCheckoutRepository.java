package com.splitur.app.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.model.promo.PromoResponse;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class JoinCheckoutRepository {
    private ApiService apiService;

    public JoinCheckoutRepository() {
    }

    public MutableLiveData<BasicModel> checkUserEmail(String id) {
        final MutableLiveData<BasicModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.checkUserEmailExistence(id);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                if (response.body() != null) {
                    BasicModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }

    public MutableLiveData<PromoResponse> apply(String code) {
        final MutableLiveData<PromoResponse> mutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PromoResponse> call = apiService.applyPromoCode(code);
        call.enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponse> call, @NonNull Response<PromoResponse> response) {
                if (response.body() != null) {
                    mutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PromoResponse> call, @NonNull Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
