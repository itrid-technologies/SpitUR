package com.splitur.app.data.repository.transactionRepository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.TransactionsModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class TransactionRepository {
    private ApiService apiService;

    public TransactionRepository() {
    }

    public MutableLiveData<TransactionsModel> transactions() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<TransactionsModel> TransactionsModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<TransactionsModel> call = apiService.getAllTransactions("Bearer " + token);
        call.enqueue(new Callback<TransactionsModel>() {
            @Override
            public void onResponse(@NonNull Call<TransactionsModel> call, @NonNull Response<TransactionsModel> response) {
                if(response.body()!=null)
                {
                    TransactionsModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<TransactionsModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return TransactionsModelMutableLiveData;
    }
}
