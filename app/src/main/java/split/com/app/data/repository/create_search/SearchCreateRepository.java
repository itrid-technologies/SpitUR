package split.com.app.data.repository.create_search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.model.search_sub_category.SearchSubCatModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

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
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Category Error",t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<PopularSubCategoryModel> getSearchedSubCategory(String data) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<PopularSubCategoryModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<PopularSubCategoryModel> call = apiService.getsubCat(token,data);
        call.enqueue(new Callback<PopularSubCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<PopularSubCategoryModel> call, @NonNull Response<PopularSubCategoryModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularSubCategoryModel> call, @NonNull Throwable t) {
                Log.e("Detail Error",t.getMessage());
            }
        });

        return liveData;
    }
}
