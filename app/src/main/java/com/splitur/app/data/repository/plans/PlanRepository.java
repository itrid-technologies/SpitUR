package com.splitur.app.data.repository.plans;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.plans.PlanModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class PlanRepository {

    private ApiService apiService;

    public PlanRepository() {
    }

    public MutableLiveData<PlanModel> getPlans(String id) {
        final MutableLiveData<PlanModel> planModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PlanModel> call = apiService.getPlans(id);
        call.enqueue(new Callback<PlanModel>() {
            @Override
            public void onResponse(Call<PlanModel> call, Response<PlanModel> response) {
                if(response.body()!=null)
                {
                    planModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PlanModel> call, Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return planModelMutableLiveData;
    }
}
