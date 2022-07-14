package split.com.app.ui.main.view.full_name;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import split.com.app.R;
import split.com.app.databinding.ActivityNameBinding;
import split.com.app.ui.main.view.otp_phone_number.OtpNumber;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.view.user_id.UserId;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;

public class Name extends AppCompatActivity {

    ActivityNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        binding.btnContinue.setOnClickListener(view -> {
            String name = binding.edName.getText().toString().trim();
            if (!name.isEmpty()){

                Constants.USER_NAME = name;


                ActivityUtil.gotoPage(Name.this, UserId.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter name");
            }
        });


    }
}