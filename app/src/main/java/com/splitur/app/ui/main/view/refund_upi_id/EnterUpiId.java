package com.splitur.app.ui.main.view.refund_upi_id;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentEnterUpiIdBinding;
import com.splitur.app.ui.main.view.WebViewActivity;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.balance_viewmodel.WalletBalanceViewModel;


public class EnterUpiId extends Fragment {

    FragmentEnterUpiIdBinding binding;
    String refundValue;
    WalletBalanceViewModel viewModel;
    Intent webViewIntent;
    private String urlTerms = "nil";
    private String urlPrivacy = "nil";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnterUpiIdBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.refundUpiToolbar.title.setText("Refund");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            refundValue = getArguments().getString("Refund_Amount");
        }
        webViewIntent = new Intent(requireContext(), WebViewActivity.class);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.termsUseText.setOnClickListener(view -> {
            if (!urlTerms.equals("nil")) {
                webViewIntent.putExtra("url", urlTerms);
                webViewIntent.putExtra("title", "Terms Of Use");
                startActivity(webViewIntent);
            }
        });

        binding.policyText.setOnClickListener(view -> {
            if (!urlPrivacy.equals("nil")) {
                webViewIntent.putExtra("url", urlPrivacy);
                webViewIntent.putExtra("title", "Privacy Policy");
                startActivity(webViewIntent);
            }
        });


        binding.btnRefund.setOnClickListener(view -> {

            String upiId = binding.edUpiId.getText().toString().trim();
            if(!upiId.isEmpty()){
                viewModel = new WalletBalanceViewModel("",upiId,refundValue);
                viewModel.initRefund();
                viewModel.getRefund_data().observe(getViewLifecycleOwner(),basicModel -> {
                    if (basicModel.isStatus().equalsIgnoreCase("true")){
                        Navigation.findNavController(view).navigate(R.id.action_enterUpiId_to_refundCompletion);
                    }
                });
            }

        });

        binding.refundUpiToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }
}