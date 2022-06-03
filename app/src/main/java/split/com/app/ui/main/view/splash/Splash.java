package split.com.app.ui.main.view.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import split.com.app.R;
import split.com.app.databinding.ActivitySplashBinding;
import split.com.app.ui.main.view.otp_phone_number.OtpNumber;
import split.com.app.utils.ActivityUtil;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.getStartedBtn.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Splash.this,OtpNumber.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });


    }
}