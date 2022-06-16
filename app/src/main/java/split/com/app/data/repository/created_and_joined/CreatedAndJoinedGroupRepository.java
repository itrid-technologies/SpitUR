package split.com.app.data.repository.created_and_joined;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class CreatedAndJoinedGroupRepository {
    private ApiService apiService;

    public CreatedAndJoinedGroupRepository() {
    }

    public MutableLiveData<GroupDetailModel> getAllCreatedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getCreatedGroups(token);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Created Groups Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<GroupDetailModel> getAllJoinedGroups() {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GroupDetailModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupDetailModel> call = apiService.getJoinedGroups(token);
        call.enqueue(new Callback<GroupDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupDetailModel> call, @NonNull Response<GroupDetailModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<GroupDetailModel> call, @NonNull Throwable t) {
                Log.e("Joined Groups Error",t.getMessage());
            }
        });

        return liveData;
    }
}
