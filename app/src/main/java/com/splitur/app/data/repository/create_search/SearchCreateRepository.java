package com.splitur.app.data.repository.create_search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.popular_subcategory.PopularSubCategoryModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class SearchCreateRepository {

    private ApiService apiService;

    public SearchCreateRepository() {
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategories() {


        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PopularSubCategoryModel> call = apiService.getPopularCategories();
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Category Error", t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<PopularSubCategoryModel> getSearchedSubCategory(String data) {


        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();

        Call<PopularSubCategoryModel> call = apiService.getsubCat(token, data);
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<PopularSubCategoryModel> getSearchedSubCategoryByCatId(String data, String id) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("category_id", id);

        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();


        Call<PopularSubCategoryModel> call = apiService.getSubCatByCatId(token, data, object);

        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

}
