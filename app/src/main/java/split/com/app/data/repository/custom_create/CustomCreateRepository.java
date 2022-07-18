package split.com.app.data.repository.custom_create;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.data.api.ApiManager;
import split.com.app.data.api.ApiService;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class CustomCreateRepository {

    private ApiService apiService;

    public CustomCreateRepository() {
    }

    public MutableLiveData<BasicModel> create(String title, String slot, String cost, String email, String password, String visibility, String caTitle, String type) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        HashMap<String, String> parms = new HashMap<>();
        parms.put("group_title",title);
        parms.put("slots",slot);
        parms.put("cost_per_member",cost);
        parms.put("email",email);
        parms.put("password",password);
        parms.put("visibility", visibility);
        parms.put("sub_cat_title",caTitle );
        parms.put("validation_type",type);


        final MutableLiveData<BasicModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.createCustomGroup("Bearer "+token,parms);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Custom Create Error",t.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<BasicModel> createByOtp(String title, String slot, String cost,  String email, String password,String visibility, String caTitle, String type, String number) {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        HashMap<String, String> parms = new HashMap<>();
        parms.put("group_title",title);
        parms.put("slots",slot);
        parms.put("cost_per_member",cost);
        parms.put("email",number);
        parms.put("visibility", visibility);
        parms.put("sub_cat_title",caTitle );
        parms.put("validation_type",type);


        final MutableLiveData<BasicModel> liveData = new MutableLiveData<>();
        apiService = ApiManager.getRestApiService();
        Call<BasicModel> call = apiService.createCustomGroup("Bearer "+token,parms);
        call.enqueue(new Callback<BasicModel>() {
            @Override
            public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                Log.e("Custom Create Error",t.getMessage());
            }
        });

        return liveData;
    }

}
