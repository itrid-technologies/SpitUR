package com.splitur.app.data.repository.razor_key;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.SecretKeyModel;

public class RazorKeyRepository {

    private ApiService apiService;

    public RazorKeyRepository() {
    }

    public MutableLiveData<SecretKeyModel> getKey() {
        final MutableLiveData<SecretKeyModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<SecretKeyModel> call = apiService.getRozarSecretKey();
        call.enqueue(new Callback<SecretKeyModel>() {
            @Override
            public void onResponse(@NonNull Call<SecretKeyModel> call, @NonNull Response<SecretKeyModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SecretKeyModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }
}
