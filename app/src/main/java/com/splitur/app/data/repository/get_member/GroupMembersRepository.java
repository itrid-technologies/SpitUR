package com.splitur.app.data.repository.get_member;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.data.model.group_member.GroupMemberModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

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


    public MutableLiveData<BasicModel1> removeGroupMember(String group_id, String user_id) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("group_id", group_id);
        object.addProperty("user_id", user_id);

        final MutableLiveData<BasicModel1> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel1> call = apiService.removeMember("Bearer "+token,object);
        call.enqueue(new Callback<BasicModel1>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel1> call, @NonNull Response<BasicModel1> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel1> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return liveData;
    }


}
