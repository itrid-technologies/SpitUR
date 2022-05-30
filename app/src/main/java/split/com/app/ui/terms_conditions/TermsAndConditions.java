package split.com.app.ui.terms_conditions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import split.com.app.R;
import split.com.app.databinding.ActivityTermsAndConditionsBinding;

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
    }
}