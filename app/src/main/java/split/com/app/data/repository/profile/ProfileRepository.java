package split.com.app.data.repository.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.update_user_profile.UserUpdateModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class ProfileRepository {
    private ApiService apiService;

    public ProfileRepository() {

    }

    public MutableLiveData<UserUpdateModel> upDateProfile(String name, String id, String avatar) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("avatar", avatar);
        object.addProperty("userId", id);


        final MutableLiveData<UserUpdateModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<UserUpdateModel> call = apiService.updateProfile("Bearer " +token, object);
        call.enqueue(new Callback<UserUpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<UserUpdateModel> call, @NonNull Response<UserUpdateModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserUpdateModel> call, @NonNull Throwable t) {
                Log.e("Update Error",t.getMessage());
            }
        });

        return liveData;
    }

}