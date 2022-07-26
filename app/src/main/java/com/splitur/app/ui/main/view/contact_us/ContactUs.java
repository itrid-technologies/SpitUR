package com.splitur.app.ui.main.view.contact_us;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentContactUsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;


public class ContactUs extends Fragment {


    FragmentContactUsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactUsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.contactToolbar.title.setText("Contact Us");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.contactToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.contactLayout.faqLayout.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_contactUs_to_FAQ);
        });

        binding.contactLayout.chatLayout.setOnClickListener(view -> {
            //to group created
            Bundle bundle = new Bundle();
            bundle.putBoolean("isFromChat", true);
            Navigation.findNavController(view).navigate(R.id.action_contactUs_to_createdAndJoinedGroups, bundle);
        });
    }
}