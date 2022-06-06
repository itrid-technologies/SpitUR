package split.com.app.data.repository.otp_number;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.data.model.basic_model.BasicModel;

public class PhoneNumberRepository {
    private ApiService apiService;


    public PhoneNumberRepository() {
    }

    public MutableLiveData<NumberModel> checkPhoneNumber(String phone_number) {
        final MutableLiveData<NumberModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getClientAuthentication().create(ApiService.class);
        Call<NumberModel> call = apiService.checkNumberExistence(phone_number);
        call.enqueue(new Callback<NumberModel>() {
            @Override
            public void onResponse(Call<NumberModel> call, Response<NumberModel> response) {
                if(response.body()!=null)
                {
                    RegisterModelMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull Call<NumberModel> call, Throwable t) {
                Log.e("Number Error",t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }

    public MutableLiveData<BasicModel> sendOtp(String otp) {
        final MutableLiveData<BasicModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getClientAuthentication().create(ApiService.class);
        Call<BasicModel> call = apiService.sendOTP(otp);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(Call<BasicModel> call, Response<BasicModel> response) {
                if(response.body()!=null)
                {
                    RegisterModelMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull Call<BasicModel> call, Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }


}
