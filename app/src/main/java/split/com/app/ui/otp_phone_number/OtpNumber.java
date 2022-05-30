package split.com.app.ui.otp_phone_number;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import split.com.app.R;
import split.com.app.databinding.ActivityOtpNumberBinding;
import split.com.app.ui.otp_verification.OtpVerification;

public class OtpNumber extends AppCompatActivity {

    ActivityOtpNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.sendOtpButton.setOnClickListener(view -> {
            String number = binding.phoneNumber.getText().toString().trim();
            if (!number.isEmpty()) {
                Intent intent = new Intent(OtpNumber.this, OtpVerification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else{
                Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show();
            }
        });

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

    }
}