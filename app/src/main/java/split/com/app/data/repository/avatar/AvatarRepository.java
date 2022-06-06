package split.com.app.data.repository.avatar;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.get_avatar.AvatarModel;

public class AvatarRepository {
    private ApiService apiService;


    public AvatarRepository() {
    }

    public MutableLiveData<AvatarModel> getAvatar() {
        final MutableLiveData<AvatarModel> avatarModelMutableLiveData = new MutableLiveData<>();

        apiService = ApiManager.getClientAuthentication().create(ApiService.class);

        Call<AvatarModel> call = apiService.getAvatar();

        call.enqueue(new Callback<AvatarModel>() {
            @Override
            public void onResponse(Call<AvatarModel> call, Response<AvatarModel> response) {
                if(response.body()!=null)
                {
                    avatarModelMutableLiveData.setValue(response.body());
                }


            }

            @Override
            public void onFailure(@NonNull Call<AvatarModel> call, Throwable t) {
                Log.e("Avatar Error",t.getMessage());
            }
        });

        return avatarModelMutableLiveData;
    }
}
