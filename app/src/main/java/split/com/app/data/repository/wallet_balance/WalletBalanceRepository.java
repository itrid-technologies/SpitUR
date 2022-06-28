package split.com.app.data.repository.wallet_balance;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.basic_model.BasicModel1;
import split.com.app.data.model.wallet_balance.WalletBalanceModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class WalletBalanceRepository {

    private ApiService apiService;

    public WalletBalanceRepository() {

    }

    public MutableLiveData<WalletBalanceModel> getBalance(String coins) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<WalletBalanceModel> WalletBalanceModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<WalletBalanceModel> call = apiService.getWalletBalance("Bearer " +token,coins);
        call.enqueue(new Callback<WalletBalanceModel>() {
            @Override
            public void onResponse(@NonNull Call<WalletBalanceModel> call, @NonNull Response<WalletBalanceModel> response) {
                if(response.body()!=null)
                {
                    WalletBalanceModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletBalanceModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return WalletBalanceModelMutableLiveData;
    }

    public MutableLiveData<BasicModel1> reFund(String upiId, String amount) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("refund", amount);
        object.addProperty("upi_id", upiId);


        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.refundAmount("Bearer " +token,object);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }

}
