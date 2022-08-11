package com.splitur.app.ui.main.view.user_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.api.ApiManager;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.databinding.ActivityUserIdBinding;
import com.splitur.app.ui.main.view.avatar.ChooseAvatar;
import com.splitur.app.ui.main.viewmodel.user_id.UserIdViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserId extends AppCompatActivity {

    ActivityUserIdBinding binding;
    private UserIdViewModel mViewModel;
    private List<BasicModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        binding.btnFinalStep.setOnClickListener(view -> {
            String userId = binding.edUserId.getText().toString().trim();
            if (!userId.isEmpty()){

                Call<BasicModel> call = ApiManager.getRestApiService().checkUserIdExistence(userId);
                call.enqueue(new Callback<BasicModel>() {
                    @Override
                    public void onResponse(@NonNull Call<BasicModel> call, @NonNull Response<BasicModel> response) {
                        if(response.body()!=null)
                        {
                            BasicModel basicModel = response.body();
                            if (!basicModel.isStatus()){

                                Constants.USER_ID = userId;

                                ActivityUtil.gotoPage(UserId.this, ChooseAvatar.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }else {
                                binding.errorMessage.setVisibility(View.VISIBLE);
                                binding.errorMessage.setText(basicModel.getMessage());
                            }                        } else if (response.code() == 400) {
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
                    public void onFailure(@NonNull Call<BasicModel> call, @NonNull Throwable t) {
                        Log.e("Avatar Error", t.getMessage());
                    }
                });

            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter userid");
            }
        });



    }
}