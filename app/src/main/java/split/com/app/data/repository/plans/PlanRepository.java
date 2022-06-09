package split.com.app.data.repository.plans;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.plans.PlanModel;
import split.com.app.data.model.register.RegisterModel;

public class PlanRepository {

    private ApiService apiService;

    public PlanRepository() {
    }

    public MutableLiveData<PlanModel> getPlans(String id) {
        final MutableLiveData<PlanModel> planModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getClientAuthentication().create(ApiService.class);
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
