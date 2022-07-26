package com.splitur.app.ui.main.view.join_plans;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentJoinPlansBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;


public class JoinPlans extends Fragment {


    String url;
    FragmentJoinPlansBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinPlansBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.joinPlansToolbar.title.setText("Plans");
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.joinPlansToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        if (getArguments() != null){
            url = getArguments().getString("plan_url");
        }

        WebSettings webSettings = binding.plansWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.plansWebview.setWebViewClient(new WebViewClient());
        binding.plansWebview.loadUrl(url);
    }
}