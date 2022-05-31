package split.com.app.ui.terms_conditions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import split.com.app.R;
import split.com.app.databinding.ActivityTermsAndConditionsBinding;
import split.com.app.ui.dashboard.Dashboard;

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
            Intent intent = new Intent(TermsAndConditions.this, Dashboard.class);
            startActivity(intent);
        });
    }
}