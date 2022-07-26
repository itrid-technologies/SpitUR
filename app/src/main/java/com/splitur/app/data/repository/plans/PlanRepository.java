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
