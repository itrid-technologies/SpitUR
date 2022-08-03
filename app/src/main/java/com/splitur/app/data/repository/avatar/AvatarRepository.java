package com.splitur.app.data.repository.avatar;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.get_avatar.AvatarModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class AvatarRepository {
    private ApiService apiService;


    public AvatarRepository() {
    }

    public MutableLiveData<AvatarModel> getAvatar() {

        final MutableLiveData<AvatarModel> avatarModelMutableLiveData = new MutableLiveData<>();

        apiService = ApiManager.getRestApiService();

        Call<AvatarModel> call = apiService.getAvatar();

        call.enqueue(new Callback<AvatarModel>() {
            @Override
            public void onResponse(@NonNull Call<AvatarModel> call, @NonNull Response<AvatarModel> response) {
                if(response.body()!=null)
                {
                    avatarModelMutableLiveData.setValue(response.body());
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
            public void onFailure(@NonNull Call<AvatarModel> call, @NonNull Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return avatarModelMutableLiveData;
    }
}
