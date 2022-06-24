package split.com.app.data.repository.faq;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.faq.FaqListModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class FaqRepository {
    private ApiService apiService;

    public FaqRepository() {
    }

    public MutableLiveData<FaqListModel> getFaqList() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<FaqListModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<FaqListModel> call = apiService.faqList("Bearer "+token);
        call.enqueue(new Callback<FaqListModel>() {
            @Override
            public void onResponse(@NonNull Call<FaqListModel> call, @NonNull Response<FaqListModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<FaqListModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }
}
