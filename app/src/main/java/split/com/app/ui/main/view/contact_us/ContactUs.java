package split.com.app.ui.main.view.contact_us;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentContactUsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class ContactUs extends Fragment {


    FragmentContactUsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactUsBinding.inflate(inflater, container, false);
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

        binding.contactLayout.faqLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_contactUs_to_FAQ);
        });
    }
}