package com.splitur.app.ui.main.view.otp_verification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivityOtpVerificationBinding;
import com.splitur.app.ui.main.view.working_slider.GroupWorkingSlider;
import com.splitur.app.ui.main.viewmodel.ReferralViewModel;
import com.splitur.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import com.splitur.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.util.Locale;

public class OtpVerification extends AppCompatActivity { //otp listener removed

    ActivityOtpVerificationBinding binding;

    private OtpVerificationViewModel mViewModel;
    boolean hasResendOTP = false;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // OtpReader.bind(this, "SOLV");


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

//                if (!clicked) {
//                    clicked = true;
//                    resendOtp(number);
//                } else {
//                    binding.remainingTime.setEnabled(false);
//                    binding.remainingTime.setTextColor(R.color.hint_color);
//                }
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
        }


    }

    private void startOtpCountDown() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.remainingTime.setText(String.format(Locale.ENGLISH, "%d sec", (int) (millisUntilFinished / 1000)));
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {
                binding.remainingTime.setText("Resend");
                binding.remainingTime.setTextColor(Color.parseColor("#FFFFFFFF"));
                if (hasResendOTP) {
                    binding.remainingTime.setVisibility(View.GONE);
                }
            }

        }.start();
    }

    private void resendOtp(String number) {
        hasResendOTP = true;
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

        mViewModel = new OtpVerificationViewModel(number, otp, OtpVerification.this);
        mViewModel.init();
        mViewModel.getData().observe(this, authenticationModel -> {
            if (authenticationModel.isSuccess()) {

                if (authenticationModel.getData().getUser() != null) {
                    Constants.ID = String.valueOf(authenticationModel.getData().getUser().getId());
                    Constants.USER_ID = authenticationModel.getData().getUser().getUserId();
                    Constants.USER_NAME = authenticationModel.getData().getUser().getName();
                    Constants.USER_AVATAR = authenticationModel.getData().getUser().getAvatar();
                    Constants.SourceId = authenticationModel.getData().getUser().getSource_id();
                    Constants.ContactId = authenticationModel.getData().getUser().getContact_id();
                    // Constants.DEVICE_TOKEN = authenticationModel.getData().getToken();

                    MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                    pm.saveData(Split.getAppContext(), "Id", String.valueOf(authenticationModel.getData().getUser().getId()));
                    pm.saveData(Split.getAppContext(), "userAccessToken", authenticationModel.getData().getToken());
                    pm.saveData(Split.getAppContext(), "userName", String.valueOf(authenticationModel.getData().getUser().getName()));
                    pm.saveData(Split.getAppContext(), "userAvatar", authenticationModel.getData().getUser().getAvatar());
                    pm.saveData(Split.getAppContext(), "source_id", authenticationModel.getData().getUser().getSource_id());
                    pm.saveData(Split.getAppContext(), "contact_id", String.valueOf(authenticationModel.getData().getUser().getContact_id()));

                    String id = pm.getData(OtpVerification.this, "Id");


                    String refer_code = pm.getData(OtpVerification.this, "ReferralCode");

                    if (!refer_code.isEmpty()) {
                        Toast.makeText(this, refer_code, Toast.LENGTH_SHORT).show();
                        ReferralViewModel referralViewModel = new ReferralViewModel(id, refer_code);
                        referralViewModel.init();
                        referralViewModel.getData().observe(this, basicModel -> {
                            if (basicModel.isStatus().equalsIgnoreCase("true")) {
                                Toast.makeText(this, basicModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            ActivityUtil.gotoPage(OtpVerification.this, GroupWorkingSlider.class);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        });
                    } else {
                        ActivityUtil.gotoPage(OtpVerification.this, GroupWorkingSlider.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }


                }

            }
        });

    }

//    @Override
//    public void otpReceived(String messageText) {
//        Toast.makeText(this, "Got " + messageText, Toast.LENGTH_LONG).show();
//        Log.d("Otp", messageText);
//        ;
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}