package split.com.app.data.repository.get_member;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.group_member.GroupMemberModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class GroupMembersRepository {
    private ApiService apiService;

    public GroupMembersRepository() {
    }

    public MutableLiveData<GroupMemberModel> getMembers(String id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        final MutableLiveData<GroupMemberModel> BasicModelMutableLiveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<GroupMemberModel> call = apiService.getGroupMembers("Bearer "+token,id);
        call.enqueue(new Callback<GroupMemberModel>() {
            @Override
            public void onResponse(@NonNull Call<GroupMemberModel> call, @NonNull Response<GroupMemberModel> response) {
                if(response.body()!=null)
                {
                    BasicModelMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupMemberModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return BasicModelMutableLiveData;
    }
}
