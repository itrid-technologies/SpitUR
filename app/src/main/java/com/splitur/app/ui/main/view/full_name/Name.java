package com.splitur.app.ui.main.view.full_name;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityNameBinding;
import com.splitur.app.ui.main.view.user_id.UserId;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;

public class Name extends AppCompatActivity {

    ActivityNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        binding.btnContinue.setOnClickListener(view -> {
            String name = binding.edName.getText().toString().trim();
            if (!name.isEmpty()){

                Constants.USER_NAME = Constants.capitalize(name);

                binding.edName.setText(Constants.USER_NAME);

                ActivityUtil.gotoPage(Name.this, UserId.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter name");
            }
        });


    }
}