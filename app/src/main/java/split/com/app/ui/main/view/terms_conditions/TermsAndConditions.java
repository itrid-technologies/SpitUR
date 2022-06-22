package split.com.app.ui.main.view.terms_conditions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.databinding.ActivityTermsAndConditionsBinding;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.register_viewmodel.RegisterViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.MySharedPreferences;

public class TermsAndConditions extends AppCompatActivity {

    private ActivityTermsAndConditionsBinding mBinding;

    private RegisterViewModel mViewModel;
    private List<RegisterModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        MySharedPreferences sharedPreferences = new MySharedPreferences(this);
        String number = sharedPreferences.getData(this,"number");
        String name = sharedPreferences.getData(this,"userName");
        String userid = sharedPreferences.getData(this,"userId");
        String avatar = sharedPreferences.getData(this,"userAvatar");



        mViewModel = new RegisterViewModel(name,number,userid,avatar);
        mViewModel.init();

        initClickListeners();
    }

    private void initClickListeners() {
        mBinding.back.setOnClickListener(view -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        mBinding.btnContinue.setOnClickListener(view -> {

            mViewModel.getData().observe(this, registerModel -> {
                if (registerModel.isSuccess()){
                    ActivityUtil.gotoPage(TermsAndConditions.this, OtpVerification.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });

        });
    }


}