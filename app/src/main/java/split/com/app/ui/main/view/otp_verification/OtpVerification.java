package split.com.app.ui.main.view.otp_verification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import split.com.app.R;
import split.com.app.databinding.ActivityOtpVerificationBinding;
import split.com.app.service.OTPListener;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.OtpReader;
import split.com.app.utils.Split;

public class OtpVerification extends AppCompatActivity implements OTPListener {

    ActivityOtpVerificationBinding binding;

    private OtpVerificationViewModel mViewModel;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    11);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }

        OtpReader.bind(this,"SOLV");



        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



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
//        String number = sharedPreferences.getData(this, "number");
        String number = Constants.USER_NUMBER;


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


                    ActivityUtil.gotoPage(OtpVerification.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void otpReceived(String messageText) {
        Toast.makeText(this,"Got "+messageText,Toast.LENGTH_LONG).show();
        Log.d("Otp",messageText);;
    }
}