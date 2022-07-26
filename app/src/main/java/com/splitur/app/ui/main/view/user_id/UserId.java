package com.splitur.app.ui.main.view.user_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.databinding.ActivityUserIdBinding;
import com.splitur.app.ui.main.view.avatar.ChooseAvatar;
import com.splitur.app.ui.main.viewmodel.user_id.UserIdViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;

public class UserId extends AppCompatActivity {

    ActivityUserIdBinding binding;
    private UserIdViewModel mViewModel;
    private List<BasicModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        binding.btnFinalStep.setOnClickListener(view -> {
            String userId = binding.edUserId.getText().toString().trim();
            if (!userId.isEmpty()){

                mViewModel = new UserIdViewModel(userId);
                mViewModel.init();
                mViewModel.getData().observe(this, basicModel -> {
                    if (!basicModel.isStatus()){

                        Constants.USER_ID = userId;

                        ActivityUtil.gotoPage(UserId.this, ChooseAvatar.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else {
                        binding.errorMessage.setVisibility(View.VISIBLE);
                        binding.errorMessage.setText(basicModel.getMessage());
                    }
                });

            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter userid");
            }
        });



    }
}