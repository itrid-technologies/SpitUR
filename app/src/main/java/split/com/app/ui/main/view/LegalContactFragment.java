package split.com.app.ui.main.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import split.com.app.R;
import split.com.app.data.api.ApiManager;
import split.com.app.data.model.settings.SettingsResponse;
import split.com.app.databinding.FragmentLegalContactBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;

public class LegalContactFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LegalContactFragment";
    private String urlTerms = "nil";
    private String urlPrivacy = "nil";
    private FragmentLegalContactBinding binding;

    public LegalContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLegalContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchSettingsFromServer();

        //bind listeners
        Dashboard.hideNav(true);
        binding.topBar.title.setText("Contact Us");
        binding.topBar.back.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.touLayout.setOnClickListener(this);
        binding.privacyLayout.setOnClickListener(this);

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
                        Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SettingsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void onClick(View view) {

        Bundle bundle = new Bundle();
        final int id = view.getId();

        if (id == R.id.tou_layout) {
            //web view terms
            if (!urlTerms.equals("nil")) {
                bundle.putString("title", "Terms Of Use");
                bundle.putString("url", urlTerms);
                navToWevViewScreen(bundle);
            }

        } else if (id == R.id.privacy_layout) {
            //web view privacy
            if (!urlPrivacy.equals("nil")) {
                bundle.putString("title", "Privacy Policy");
                bundle.putString("url", urlPrivacy);
                navToWevViewScreen(bundle);
            }
        }
    }

    private void navToWevViewScreen(Bundle bundle) {
        Navigation.findNavController(requireView()).navigate(R.id.action_legalContactFragment_to_webViewFragment, bundle);
    }

}