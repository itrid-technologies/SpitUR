package split.com.app.ui.main.view.user_id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import split.com.app.R;
import split.com.app.databinding.ActivityUserIdBinding;
import split.com.app.ui.main.view.avatar.ChooseAvatar;
import split.com.app.ui.main.view.full_name.Name;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.MySharedPreferences;

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

                MySharedPreferences sharedPreferences = new MySharedPreferences(this);
                sharedPreferences.saveData(this,"user_id",userId);

                ActivityUtil.gotoPage(UserId.this, ChooseAvatar.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                binding.edUserId.setError("Enter userid");
            }
        });



    }
}