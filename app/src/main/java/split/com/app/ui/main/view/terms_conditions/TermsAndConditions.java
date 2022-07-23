package split.com.app.ui.main.view.terms_conditions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.R;
import split.com.app.data.api.ApiManager;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.settings.SettingsResponse;
import split.com.app.databinding.ActivityTermsAndConditionsBinding;
import split.com.app.ui.main.view.WebViewActivity;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.register_viewmodel.RegisterViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;

public class TermsAndConditions extends AppCompatActivity {

    private ActivityTermsAndConditionsBinding mBinding;

    private RegisterViewModel mViewModel;
    private List<RegisterModel> modelList = new ArrayList<>();
    private boolean isTermAgreed = false;
    private boolean isPrivacyAgreed = false;
    private String urlTerms = "nil";
    private String urlPrivacy = "nil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTermsAndConditionsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        String number = Constants.USER_NUMBER;
        String name = Constants.USER_NAME;
        String userid = Constants.USER_ID;
        String avatar = Constants.USER_AVATAR;


        mViewModel = new RegisterViewModel(name, number, userid, avatar);
        mViewModel.init();

        fetchSettingsFromServer();
        initClickListeners();
    }

    private void initClickListeners() {
        mBinding.back.setOnClickListener(view -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        mBinding.btnContinue.setOnClickListener(view -> {

            if (!isTermAgreed) {
                Toast.makeText(this, "Please agree to Terms & Conditions.", Toast.LENGTH_SHORT).show();
            } else if (!isPrivacyAgreed) {
                Toast.makeText(this, "Please agree to Privacy Policy.", Toast.LENGTH_SHORT).show();
            } else {
                mViewModel.getData().observe(this, registerModel -> {
                    if (registerModel.isSuccess()) {
                        ActivityUtil.gotoPage(TermsAndConditions.this, OtpVerification.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }

        });

        mBinding.cbTerms.setOnCheckedChangeListener((compoundButton, isChecked) -> isTermAgreed = isChecked);

        mBinding.cbPrivacy.setOnCheckedChangeListener((compoundButton, isChecked) -> isPrivacyAgreed = isChecked);

        Intent webViewIntent = new Intent(TermsAndConditions.this, WebViewActivity.class);
        mBinding.tvTerms.setOnClickListener(view -> {
            if (!urlTerms.equals("nil")) {
                webViewIntent.putExtra("url", urlTerms);
                webViewIntent.putExtra("title", "Terms Of Use");
                startActivity(webViewIntent);
            }
        });

        mBinding.tvPrivacy.setOnClickListener(view -> {
            if (!urlPrivacy.equals("nil")) {
                webViewIntent.putExtra("url", urlPrivacy);
                webViewIntent.putExtra("title", "Privacy Policy");
                startActivity(webViewIntent);
            }
        });

    }

    private void fetchSettingsFromServer() {
        Call<SettingsResponse> call = ApiManager.getRestApiService().getSettings();
        call.enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingsResponse> call, @NonNull Response<SettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        urlTerms = response.body().getData().getTermsAndConditionsUrl();
                        urlPrivacy = response.body().getData().getPrivayUrl();
                    } else {
                        Toast.makeText(TermsAndConditions.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsResponse> call, @NonNull Throwable t) {
                Log.e("ActivityTerms", "onFailure: ", t);
            }
        });
    }

}