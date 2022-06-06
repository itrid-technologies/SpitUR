package split.com.app.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.RegisterModel;
import split.com.app.data.model.phone_number.NumberModel;

public class RegisterRepository {

    private ApiService apiService;

    public RegisterRepository() {
    }

    public MutableLiveData<RegisterModel> registerUser(String number, String name, String id, String url) {
        final MutableLiveData<RegisterModel> RegisterModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getClientAuthentication().create(ApiService.class);
        Call<RegisterModel> call = apiService.register(number,name,url,id);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.body()!=null)
                {
                    RegisterModelMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull Call<RegisterModel> call, Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return RegisterModelMutableLiveData;
    }
}
