package split.com.app.data.repository.group_information;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.group_score.GroupScoreModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class GroupInformationRepository {
    private ApiService apiService;

    public GroupInformationRepository() {

    }

    public MutableLiveData<GroupScoreModel> getGroupScore(String groupId) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");


        final MutableLiveData<GroupScoreModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupScoreModel> call = apiService.getGroupScore("Bearer " + token, groupId);
        call.enqueue(new Callback<GroupScoreModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupScoreModel> call, @NonNull Response<GroupScoreModel> response) {
                if (response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupScoreModel> call, @NonNull Throwable t) {
                Log.e("Score Error", t.getMessage());
            }
        });

        return liveData;
    }

}
