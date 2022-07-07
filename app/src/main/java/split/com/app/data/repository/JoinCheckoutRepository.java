package split.com.app.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.PromoModel;
import split.com.app.data.model.basic_model.BasicModel;

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
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }

    public MutableLiveData<PromoModel> apply(String code) {
        final MutableLiveData<PromoModel> mutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PromoModel> call = apiService.applyPromoCode(code);
        call.enqueue(new Callback<PromoModel>() {
            @Override
            public void onResponse(@NonNull Call<PromoModel> call, @NonNull Response<PromoModel> response) {
                if(response.body()!=null)
                {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return mutableLiveData;
    }

}
