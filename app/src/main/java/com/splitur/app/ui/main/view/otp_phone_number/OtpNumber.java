package com.splitur.app.ui.main.view.otp_phone_number;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityOtpNumberBinding;
import com.splitur.app.ui.main.view.full_name.Name;
import com.splitur.app.ui.main.view.otp_verification.OtpVerification;
import com.splitur.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;

public class OtpNumber extends AppCompatActivity {

    ActivityOtpNumberBinding binding;

    private PhoneNumberViewModel mViewModel;
    String my_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.sendOtpButton.setOnClickListener(view -> {


            String number = binding.phoneNumber.getText().toString().trim();
            String code = binding.countryCodePicker.getSelectedCountryCode().trim();
            if (!number.isEmpty()) {

                my_number = "+"+code+number;

                mViewModel = new PhoneNumberViewModel(my_number);
                mViewModel.init();

                mViewModel.getData().observe(this, numberModel -> {
                    if (numberModel.isStatus()){

                        sentOtp();


                    }else {

                        Constants.USER_NUMBER = my_number;

                        ActivityUtil.gotoPage(OtpNumber.this,Name.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });


            }else{
                binding.errorMessage.setText("Enter number");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });


    }

    private void sentOtp() {
        mViewModel.initOtp();
        mViewModel.getOtp_data().observe(this, otpModel -> {
            if (otpModel.isStatus()){

                Constants.USER_NUMBER = my_number;

                ActivityUtil.gotoPage(OtpNumber.this,OtpVerification.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText(otpModel.getMessage());
            }
        });

    }
}