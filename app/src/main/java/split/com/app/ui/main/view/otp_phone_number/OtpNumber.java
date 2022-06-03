package split.com.app.ui.main.view.otp_phone_number;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.RegisterModel;
import split.com.app.data.model.get_avatar.AvatarItem;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.model.phone_number.NumberModel;
import split.com.app.databinding.ActivityOtpNumberBinding;
import split.com.app.ui.main.view.full_name.Name;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.view.splash.Splash;
import split.com.app.ui.main.viewmodel.avatar_viewmodel.AvatarViewModel;
import split.com.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.MySharedPreferences;

public class OtpNumber extends AppCompatActivity {

    ActivityOtpNumberBinding binding;

    private PhoneNumberViewModel mViewModel;
    private List<NumberModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new PhoneNumberViewModel();
        mViewModel.init();

        binding.sendOtpButton.setOnClickListener(view -> {
            String number = binding.phoneNumber.getText().toString().trim();
            if (!number.isEmpty()) {

                mViewModel.getData().observe(this, numberModel -> {
                    if (numberModel.isStatus()){

                        ActivityUtil.gotoPage(OtpNumber.this,OtpVerification.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else {

                        MySharedPreferences preferences = new MySharedPreferences(this);
                        preferences.saveData(this,"number",number);

                        ActivityUtil.gotoPage(OtpNumber.this,Name.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });


            }else{
                binding.phoneNumber.setError("Enter number");
            }
        });

        binding.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });




    }
}