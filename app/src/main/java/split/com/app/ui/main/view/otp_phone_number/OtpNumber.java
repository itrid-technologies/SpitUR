package split.com.app.ui.main.view.otp_phone_number;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.databinding.ActivityOtpNumberBinding;
import split.com.app.ui.main.view.full_name.Name;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;

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
            String code = binding.countryCodePicker.getSelectedCountryCode().toString().trim();
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