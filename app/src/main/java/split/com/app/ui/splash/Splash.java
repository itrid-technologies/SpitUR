package split.com.app.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.skydoves.elasticviews.ElasticButton;

import split.com.app.R;
import split.com.app.databinding.ActivitySplashBinding;
import split.com.app.ui.otp_phone_number.OtpNumber;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.getStartedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Splash.this, OtpNumber.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });


    }
}