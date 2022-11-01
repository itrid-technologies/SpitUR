package com.splitur.app.ui.main.view.splash;


import static com.splitur.app.utils.RefererDataReciever.REFERRER_DATA;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.splitur.app.R;
import com.splitur.app.databinding.ActivitySplashBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.otp_phone_number.OtpNumber;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.RefererDataReciever;
import com.tenjin.android.TenjinSDK;

public class Splash extends AppCompatActivity {

    TenjinSDK instance;
    ActivitySplashBinding binding;
    InstallReferrerClient mReferrerClient;
    MySharedPreferences sharedPreferences;
    BroadcastReceiver mUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());


        sharedPreferences = new MySharedPreferences(Splash.this);
        instance = TenjinSDK.getInstance(this, "2EGFJZAMRKEJT16YVSVVD5ASGRGJWWWR");
        instance.setAppStore(TenjinSDK.AppStoreType.googleplay);
        instance.connect();



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //bundle must contain all info sent in "data" field of the notification
            final String type = bundle.getString("type", "nil");
            if (!type.equals("nil")) {
                if (type.equalsIgnoreCase("otp_request") || type.equalsIgnoreCase("new_message") ) {
                    String si = bundle.getString("sender_id", "nil");
                    String gi = bundle.getString("group_id", "nil");
                    Intent chatInt = new Intent(this, Dashboard.class);
                    chatInt.putExtra("chat_intent", "ci");
                    chatInt.putExtra("sender_id", si);
                    chatInt.putExtra("group_id", gi);
                    startActivity(chatInt);
                    finish();
                } else if (type.equalsIgnoreCase("new_group_message")){
                    String gi = bundle.getString("group_id", "nil");
                    Intent chatInt = new Intent(this, Dashboard.class);
                    chatInt.putExtra("groupChat_intent", "ci");
                    chatInt.putExtra("group_id", gi);
                    startActivity(chatInt);
                    finish();
                    finish();
                }
            }
        }


        //getReferralCode();

        receiveRefferCode();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {
            navToOtpScreen();
        });



    }

    private void receiveRefferCode() {
        mUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               String referCode = getReferer(Splash.this);
                sharedPreferences.saveData(Splash.this,"ReferralCode",referCode);
                Toast.makeText(Splash.this, referCode, Toast.LENGTH_SHORT).show();
            }
        };
    }

    public static String getReferer(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        if (!sp.contains(REFERRER_DATA)) {
            return "Didn't got any referrer follow instructions :)";
        }
        return sp.getString(REFERRER_DATA, null);
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

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdateReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdateReceiver, new IntentFilter(RefererDataReciever.ACTION_UPDATE_DATA));
        super.onResume();
    }
}