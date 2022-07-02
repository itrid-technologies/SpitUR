package split.com.app.ui.main.view.splash;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Splash.this, OtpNumber.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });


    }
}