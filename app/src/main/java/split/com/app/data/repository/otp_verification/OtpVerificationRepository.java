package split.com.app.data.repository.otp_verification;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.data.model.register.RegisterModel;

public class OtpVerificationRepository {
    private ApiService apiService;

    public OtpVerificationRepository() {
    }

    public MutableLiveData<AuthenticationModel> verifyUser(String number, String otp) {
        final MutableLiveData<AuthenticationModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getClientAuthentication().create(ApiService.class);
        Call<AuthenticationModel> call = apiService.authenticateUser(number,otp);
        call.enqueue(new Callback<AuthenticationModel>() {
            @Override
            public void onResponse(Call<AuthenticationModel> call, Response<AuthenticationModel> response) {
                if(response.body()!=null)
                {
                    RegisterModelMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull Call<AuthenticationModel> call, Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }
}
