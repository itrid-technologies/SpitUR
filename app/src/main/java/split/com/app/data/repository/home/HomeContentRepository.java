package split.com.app.data.repository.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class HomeContentRepository {

    private ApiService apiService;

    public HomeContentRepository() {
    }

    public MutableLiveData<HomeContentModel> getHomeContent() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<HomeContentModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<HomeContentModel> call = apiService.getHomeData(token);
        call.enqueue(new Callback<HomeContentModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeContentModel> call, @NonNull Response<HomeContentModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<HomeContentModel> call, @NonNull Throwable t) {
                Log.e("Category Error",t.getMessage());
            }
        });

        return liveData;
    }
}
