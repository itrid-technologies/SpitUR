package com.splitur.app.ui.main.view.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.splitur.app.R;
import com.splitur.app.databinding.ActivitySplashBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.otp_phone_number.OtpNumber;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.MySharedPreferences;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;
    InstallReferrerClient mReferrerClient;
    MySharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());


        sharedPreferences = new MySharedPreferences(Splash.this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //bundle must contain all info sent in "data" field of the notification
            final String type = bundle.getString("type", "nil");
            if (!type.equals("nil")) {
                if (type.equalsIgnoreCase("otp_request")) {
                    String si = bundle.getString("sender_id", "nil");
                    String gi = bundle.getString("group_id", "nil");
                    Intent chatInt = new Intent(this, Dashboard.class);
                    chatInt.putExtra("chat_intent", "ci");
                    chatInt.putExtra("sender_id", si);
                    chatInt.putExtra("group_id", gi);
                    startActivity(chatInt);
                    finish();
                } else if (type.equalsIgnoreCase("new_message") ||
                        type.equalsIgnoreCase("new_group_message")
                ) {
                    Intent chatInt = new Intent(this, Dashboard.class);
//                    chatInt.putExtra("chat_intent", "ci"); TODO;//data
                    startActivity(chatInt);
                    finish();
                }
            }
        }


        getReferralCode();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {
            navToOtpScreen();
        });



    }

    private void getReferralCode() {
        mReferrerClient = InstallReferrerClient.newBuilder(this).build();
        mReferrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established
                        try {
                            ReferrerDetails response =
                                    mReferrerClient.getInstallReferrer();
                            if (response.getInstallReferrer().contains("referrer")){

                                sharedPreferences.saveData(Splash.this,"ReferralCode",response.getInstallReferrer());
                                Toast.makeText(Splash.this, response.getInstallReferrer(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mReferrerClient.endConnection();
                        break;
                    case
                            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app
                        break;
                    case
                            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection could not be established
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }


    private void navToOtpScreen() {

        ActivityUtil.gotoPage(Splash.this, OtpNumber.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}