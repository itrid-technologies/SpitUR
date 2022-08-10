package com.splitur.app.data.repository.otp_verification;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.otp_verification.AuthenticationModel;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationRepository {
    private ApiService apiService;

    public OtpVerificationRepository() {
    }

    public MutableLiveData<AuthenticationModel> verifyUser(String number, String otp, Activity activity) {
        final MutableLiveData<AuthenticationModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<AuthenticationModel> call = apiService.authenticateUser(Constants.DEVICE_TOKEN, number, otp);
        call.enqueue(new Callback<AuthenticationModel>() {
            @Override
            public void onResponse(Call<AuthenticationModel> call, Response<AuthenticationModel> response) {
                if (response.body() != null) {

                    RegisterModelMutableLiveData.setValue(response.body());

                } else if (response.code() == 400) {
                    if (response.errorBody() != null) {
                        Constants.getApiError1(activity,response.errorBody());

                    }
                } else if (response.code() == 500) {
                    if (response.errorBody() != null) {
                        Constants.getApiError1(activity,response.errorBody());

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AuthenticationModel> call, Throwable t) {
                Log.e("Avatar Error", t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }
}
