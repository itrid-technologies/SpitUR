package com.splitur.app.ui.main.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSucessfullySwapedBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Split;


public class SuccessfullySwapped extends Fragment {


    FragmentSucessfullySwapedBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSucessfullySwapedBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.swapSuccessToolbar.title.setText("Swap");
        binding.swapSuccessToolbar.back.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(Split.getAppContext()).load(R.drawable.success_gif).into(binding.swapSuccessGif);


        binding.btnprofile.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.profile2);
        });
    }
}