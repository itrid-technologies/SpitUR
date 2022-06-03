package split.com.app.ui.main.view.terms_conditions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import split.com.app.databinding.ActivityTermsAndConditionsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.MySharedPreferences;

public class TermsAndConditions extends AppCompatActivity {

    private ActivityTermsAndConditionsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initClickListeners();
    }

    private void initClickListeners() {
        mBinding.back.setOnClickListener(view -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        mBinding.btnContinue.setOnClickListener(view -> {

            MySharedPreferences sharedPreferences = new MySharedPreferences(this);
            String number = sharedPreferences.getData(this,"number");
            String name = sharedPreferences.getData(this,"name");
            String userid = sharedPreferences.getData(this,"user_id");
            String avatar = sharedPreferences.getData(this,"avatar");

            Intent intent = new Intent(TermsAndConditions.this, Dashboard.class);
            startActivity(intent);
        });
    }


}