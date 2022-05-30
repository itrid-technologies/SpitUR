package split.com.app.ui.user_id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import split.com.app.R;
import split.com.app.databinding.ActivityUserIdBinding;
import split.com.app.ui.avatar.ChooseAvatar;
import split.com.app.ui.otp_phone_number.OtpNumber;
import split.com.app.ui.splash.Splash;

public class UserId extends AppCompatActivity {

    ActivityUserIdBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        binding.btnContinue.setOnClickListener(view -> {
            String userId = binding.edUserId.getText().toString().trim();
            if (!userId.isEmpty()){
                Intent intent = new Intent(UserId.this, ChooseAvatar.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                Toast.makeText(this, "Enter User Id", Toast.LENGTH_SHORT).show();
            }
        });



    }
}