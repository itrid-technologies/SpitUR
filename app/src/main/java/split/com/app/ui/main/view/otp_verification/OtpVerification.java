package split.com.app.ui.main.view.otp_verification;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.otp_verification.AuthenticationModel;
import split.com.app.databinding.ActivityOtpVerificationBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;

public class OtpVerification extends AppCompatActivity {

    ActivityOtpVerificationBinding binding;

    private OtpVerificationViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.remainingTime.setText(String.valueOf((int) (millisUntilFinished / 1000) + " sec"));
            }

            public void onFinish() {
                binding.remainingTime.setText("Resend");
            }

        }.start();

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        MySharedPreferences sharedPreferences = new MySharedPreferences(this);
        String number = sharedPreferences.getData(this, "number");


        binding.remainingTime.setOnClickListener(view -> {
            String resend = binding.remainingTime.getText().toString().toString();
            if (resend.equalsIgnoreCase("Resend")) {
                String otp = binding.otp.getText().toString();

                authenticateUser(number, otp);
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

    private void authenticateUser(String number, String otp) {

        mViewModel = new OtpVerificationViewModel(number, otp);
        mViewModel.init();
        mViewModel.getData().observe(this, authenticationModel -> {
            if (authenticationModel.isSuccess()) {

                if (authenticationModel.getData().getUser() != null) {
                    MySharedPreferences preferences = new MySharedPreferences(this);
                    preferences.saveData(this, "userId", authenticationModel.getData().getUser().getUserId());
                    preferences.saveData(this, "userOtp", authenticationModel.getData().getUser().getOtpHash());
                    preferences.saveData(this, "userName", authenticationModel.getData().getUser().getName());
                    preferences.saveData(this, "userAvatar", Constants.IMG_PATH + authenticationModel.getData().getUser().getAvatar());
                    preferences.saveData(this, "userCreatedAt", authenticationModel.getData().getUser().getCreatedAt());
                    preferences.saveData(this, "userAccessToken", "Bearer " +authenticationModel.getData().getToken());

                    ActivityUtil.gotoPage(OtpVerification.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }
        });

    }
}