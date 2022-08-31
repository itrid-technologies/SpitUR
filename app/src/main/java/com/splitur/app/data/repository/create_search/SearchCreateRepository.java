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
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class SearchCreateRepository {

    private ApiService apiService;

    public SearchCreateRepository() {
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategories() {


        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();

        final JsonObject obj = new JsonObject();
        obj.addProperty("page","1");


        Call<PopularSubCategoryModel> call = apiService.getPopularCategoriesHome(obj);
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Category Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<PopularSubCategoryModel> getPopularCategoriesById(String cat_id, String page) {

        final JsonObject obj = new JsonObject();
        obj.addProperty("category_id",cat_id);
        obj.addProperty("page",page);


        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PopularSubCategoryModel> call = apiService.getPopularCategoriesById(obj);
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Category Error", t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<PopularSubCategoryModel> getSearchedSubCategory(String value, String page) {


        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();

        Call<PopularSubCategoryModel> call = apiService.getsubCat("Bearer "+token, value);
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<PopularSubCategoryModel> getSearchedSubCategoryByCatId(String data, String id, String page) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("category_id", id);
        object.addProperty("page",page);

        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();


        Call<PopularSubCategoryModel> call = apiService.getSubCatByCatId("Bearer "+token, data, object);

        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Detail Error", t.getMessage());
            }
        });

        return liveData;
    }

}
