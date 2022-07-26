package com.splitur.app.data.repository.categories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.home_categories.CategoriesModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class CategoryRepository {


    private ApiService apiService;

    public CategoryRepository() {
    }

    public MutableLiveData<CategoriesModel> getCategories() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<CategoriesModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<CategoriesModel> call = apiService.getAllCategories(token);
        call.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesModel> call, @NonNull Response<CategoriesModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CategoriesModel> call, @NonNull Throwable t) {
                Log.e("Category Error",t.getMessage());
            }
        });

        return liveData;
    }
}
