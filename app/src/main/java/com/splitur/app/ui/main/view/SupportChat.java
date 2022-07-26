package com.splitur.app.ui.main.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSupportChatBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;


public class SupportChat extends Fragment {


    FragmentSupportChatBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = com.splitur.app.databinding.FragmentSupportChatBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.scToolbar.title.setText("Chat Support");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickEvents();
    }

    private void clickEvents() {

        binding.scToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }
}