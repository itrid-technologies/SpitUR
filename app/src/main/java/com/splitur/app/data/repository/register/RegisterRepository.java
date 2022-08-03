package com.splitur.app.data.repository.register;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.register.RegisterModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class RegisterRepository {

    private ApiService apiService;

    public RegisterRepository() {
    }

    public MutableLiveData<RegisterModel> registerUser(String number, String name, String id, String url) {
        final MutableLiveData<RegisterModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<RegisterModel> call = apiService.register(number,name,url,id);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(@NonNull Call<RegisterModel> call, @NonNull Response<RegisterModel> response) {
                if(response.body()!=null)
                {
                    RegisterModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }
}
