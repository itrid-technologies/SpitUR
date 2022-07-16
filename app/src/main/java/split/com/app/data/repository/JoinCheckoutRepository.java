package split.com.app.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.promo.PromoResponse;

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
