package split.com.app.ui.full_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import split.com.app.R;
import split.com.app.databinding.ActivityNameBinding;
import split.com.app.ui.otp_verification.OtpVerification;
import split.com.app.ui.user_id.UserId;

public class Name extends AppCompatActivity {

    ActivityNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        binding.btnContinue.setOnClickListener(view -> {
            String name = binding.edName.getText().toString().trim();
            if (!name.isEmpty()){
                Intent intent = new Intent(Name.this, UserId.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            }
        });


    }
}