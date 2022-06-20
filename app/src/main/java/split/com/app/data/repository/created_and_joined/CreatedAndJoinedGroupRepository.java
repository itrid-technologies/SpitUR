package split.com.app.data.repository.created_and_joined;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.all_created_groupx.AllCreatedGroupModel;
import split.com.app.data.model.all_joined_groups.AllJoinedGroupModel;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class CreatedAndJoinedGroupRepository {
    private ApiService apiService;

    public CreatedAndJoinedGroupRepository() {
    }

    public MutableLiveData<AllCreatedGroupModel> getAllCreatedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<AllCreatedGroupModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<AllCreatedGroupModel> call = apiService.getCreatedGroups("Bearer "+token);
        call.enqueue(new Callback<AllCreatedGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<AllCreatedGroupModel> call, @NonNull Response<AllCreatedGroupModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<AllCreatedGroupModel> call, @NonNull Throwable t) {
                Log.e("Created Groups Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<AllJoinedGroupModel> getAllJoinedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<AllJoinedGroupModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<AllJoinedGroupModel> call = apiService.getJoinedGroups("Bearer "+token);
        call.enqueue(new Callback<AllJoinedGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<AllJoinedGroupModel> call, @NonNull Response<AllJoinedGroupModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<AllJoinedGroupModel> call, @NonNull Throwable t) {
                Log.e("Joined Groups Error",t.getMessage());
            }
        });

        return liveData;
    }
}
