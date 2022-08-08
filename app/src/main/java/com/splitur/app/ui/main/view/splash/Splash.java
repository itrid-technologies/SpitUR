package com.splitur.app.ui.main.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.splitur.app.R;
import com.splitur.app.databinding.ActivitySplashBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.otp_phone_number.OtpNumber;
import com.splitur.app.utils.ActivityUtil;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());

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


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {

            navToOtpScreen();
        });

    }


    private void navToOtpScreen() {

        ActivityUtil.gotoPage(Splash.this, OtpNumber.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}