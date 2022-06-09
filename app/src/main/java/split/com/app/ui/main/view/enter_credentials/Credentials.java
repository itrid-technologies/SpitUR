package split.com.app.ui.main.view.enter_credentials;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import split.com.app.R;
import split.com.app.databinding.FragmentCredentialsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Credentials extends Fragment {


    FragmentCredentialsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCredentialsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnNext.setOnClickListener(view -> {
            String email = binding.edUsername.getText().toString().trim();
            String password = binding.edPassword.getText().toString().trim();

            if (email.isEmpty()){
                binding.errorMessage.setText("Enter email");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.errorMessage.setText("Enter valid email");
                binding.errorMessage.setVisibility(View.VISIBLE);
            } else if (password.isEmpty()) {
                binding.errorMessage.setText("Enter password");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }else {
                binding.errorMessage.setVisibility(View.GONE);
                MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                pm.saveData(Split.getAppContext(), "EMAIL", email);
                pm.saveData(Split.getAppContext(), "PASSWORD", password);
                Navigation.findNavController(view).navigate(R.id.action_credentials2_to_phoneCredentials);
            }

        });
    }
}