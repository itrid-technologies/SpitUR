package split.com.app.ui.otp_verification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import split.com.app.R;
import split.com.app.databinding.ActivityOtpVerificationBinding;
import split.com.app.ui.full_name.Name;

public class OtpVerification extends AppCompatActivity {

    ActivityOtpVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        binding.verifyButton.setOnClickListener(view -> {
            String otp = binding.otp.getText().toString();
            if (!otp.isEmpty()) {
                if (otp.length() == 4) {
                    Intent intent = new Intent(OtpVerification.this, Name.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(this, "Enter Complete Digits", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();

            }

        });


    }
}