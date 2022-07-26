package com.splitur.app.ui.main.view.otp_verification;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityOtpVerificationBinding;
import com.splitur.app.service.OTPListener;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.ReferralViewModel;
import com.splitur.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import com.splitur.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.OtpReader;
import com.splitur.app.utils.Split;

public class OtpVerification extends AppCompatActivity implements OTPListener {

    ActivityOtpVerificationBinding binding;

    private OtpVerificationViewModel mViewModel;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OtpReader.bind(this, "SOLV");

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        startOtpCountDown();

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        MySharedPreferences sharedPreferences = new MySharedPreferences(this);
//        String number = sharedPreferences.getData(this, "number");
        String number = Constants.USER_NUMBER;


        binding.remainingTime.setOnClickListener(view -> {
            String resend = binding.remainingTime.getText().toString();
            if (resend.equalsIgnoreCase("Resend")) {

                resendOtp(number);
            }
        });

        binding.verifyButton.setOnClickListener(view -> {
            String otp = binding.otp.getText().toString();

            if (!otp.isEmpty()) {
                if (otp.length() == 4) {

                    authenticateUser(number, otp);


                } else {
                    binding.errorMessage.setText("Enter Complete Digits");
                    binding.errorMessage.setVisibility(View.VISIBLE);
                }

            } else {
                binding.errorMessage.setText("Enter OTP");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }

        });


    }

    private void startOtpCountDown() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.remainingTime.setText(String.format(Locale.ENGLISH, "%d sec", (int) (millisUntilFinished / 1000)));
            }

            public void onFinish() {
                binding.remainingTime.setText("Resend");
            }

        }.start();
    }

    private void resendOtp(String number) {
        PhoneNumberViewModel phoneNumberViewModel = new PhoneNumberViewModel(number);
        phoneNumberViewModel.initOtp();
        phoneNumberViewModel.getOtp_data().observe(this, otpModel -> {
            if (otpModel.isStatus()) {
                startOtpCountDown();
            } else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText(otpModel.getMessage());
            }
        });
    }


    private void authenticateUser(String number, String otp) {

        mViewModel = new OtpVerificationViewModel(number, otp);
        mViewModel.init();
        mViewModel.getData().observe(this, authenticationModel -> {
            if (authenticationModel.isSuccess()) {

                if (authenticationModel.getData().getUser() != null) {
                    Constants.ID = String.valueOf(authenticationModel.getData().getUser().getId());
                    Constants.USER_ID = authenticationModel.getData().getUser().getUserId();
                    Constants.USER_NAME = authenticationModel.getData().getUser().getName();
                    Constants.USER_AVATAR = Constants.IMG_PATH + authenticationModel.getData().getUser().getAvatar();
                    // Constants.DEVICE_TOKEN = authenticationModel.getData().getToken();

                    MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                    pm.saveData(Split.getAppContext(), "Id", String.valueOf(authenticationModel.getData().getUser().getId()));
                    pm.saveData(Split.getAppContext(), "userAccessToken", authenticationModel.getData().getToken());
                    pm.saveData(Split.getAppContext(), "userName", String.valueOf(authenticationModel.getData().getUser().getName()));
                    pm.saveData(Split.getAppContext(), "userAvatar", authenticationModel.getData().getUser().getAvatar());


                    if (!Constants.Referrer.isEmpty()){
                        ReferralViewModel referralViewModel = new ReferralViewModel(Constants.ID,Constants.Referrer);
                        referralViewModel.init();
                        referralViewModel.getData().observe(this,basicModel -> {
                            if (basicModel.isStatus().equalsIgnoreCase("true")){
                                Toast.makeText(this, basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                                ActivityUtil.gotoPage(OtpVerification.this, Dashboard.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        });
                    }else {
                        ActivityUtil.gotoPage(OtpVerification.this, Dashboard.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }


                }

            }
        });

    }

    @Override
    public void otpReceived(String messageText) {
        Toast.makeText(this, "Got " + messageText, Toast.LENGTH_LONG).show();
        Log.d("Otp", messageText);
        ;
    }
}