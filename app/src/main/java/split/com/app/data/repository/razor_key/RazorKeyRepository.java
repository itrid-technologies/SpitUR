package split.com.app.data.repository.razor_key;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.SecretKeyModel;
import split.com.app.data.model.basic_model.BasicModel;

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
