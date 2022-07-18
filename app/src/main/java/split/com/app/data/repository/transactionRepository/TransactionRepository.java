package split.com.app.data.repository.transactionRepository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.TransactionsModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

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
