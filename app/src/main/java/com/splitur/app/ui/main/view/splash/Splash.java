package com.splitur.app.ui.main.view.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.Dash;
import com.splitur.app.R;
import com.splitur.app.databinding.ActivitySplashBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.otp_phone_number.OtpNumber;
import com.splitur.app.utils.ActivityUtil;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {

            navToOtpScreen();
        });

    }




    private void navToOtpScreen() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        if (!token.isEmpty()){
            ActivityUtil.gotoPage(Splash.this, Dashboard.class);
            Log.e("Access Token", token);
        }else {

            ActivityUtil.gotoPage(Splash.this, OtpNumber.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}