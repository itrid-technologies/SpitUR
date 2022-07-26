package com.splitur.app.data.repository.create_group;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.api.ApiService;
import com.splitur.app.data.model.create_group.CreateGroupModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class CreateGroupRepository {

    ApiService apiService;

    public CreateGroupRepository() {
    }

    public MutableLiveData<CreateGroupModel> createGroup(String plan_id,String title, String slot, String cost, String email, String password, String visibility,String sub_cat_id) {

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

//        DataRequiredForGroup requiredForGroup = new DataRequiredForGroup();
//        requiredForGroup.setPlansId(Integer.parseInt(plan_id));
//        requiredForGroup.setEmail(email);
//        requiredForGroup.setPassword(password);
//        requiredForGroup.setSlots(Integer.parseInt(slot));
//        requiredForGroup.setTitle(title);
//        requiredForGroup.setCostPerMember(Integer.parseInt(cost));
//        requiredForGroup.setVisibility(Integer.parseInt(visibility));

        HashMap<String, String> parms = new HashMap<>();
        parms.put("plans_id",plan_id);
        parms.put("group_title",title);
        parms.put("slots",slot);
        parms.put("cost_per_member",cost);
        parms.put("email",email);
        parms.put("password",password);
        parms.put("visibility",visibility);
        parms.put("sub_category_id",sub_cat_id);


//        JsonObject object = new JsonObject();
//        object.addProperty("plans_id",plan_id);
//        object.addProperty("group_title",title);
//        object.addProperty("slots",slot);
//        object.addProperty("cost_per_member",cost);
//        object.addProperty("email",email);
//        object.addProperty("password",password);
//        object.addProperty("visibility",visibility);

        apiService = ApiManager.getRestApiService();

        final MutableLiveData<CreateGroupModel> liveData = new MutableLiveData<>();
       // apiService = ApiManager.getClientAuthentication().create(ApiService.class);
        Call<CreateGroupModel> call = apiService.createGroup("Bearer "+token,parms);
        call.enqueue(new Callback<CreateGroupModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateGroupModel> call, @NonNull Response<CreateGroupModel> response) {
                if(response.body()!=null)
                {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateGroupModel> call, @NonNull Throwable t) {
                Log.e("Category Error",t.getMessage());
            }
        });

        return liveData;
    }
}

