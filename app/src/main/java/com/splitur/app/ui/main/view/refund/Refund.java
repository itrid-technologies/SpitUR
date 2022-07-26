package com.splitur.app.ui.main.view.refund;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentRefundBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.balance_viewmodel.WalletBalanceViewModel;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;


public class Refund extends Fragment {

    FragmentRefundBinding binding;
    WalletBalanceViewModel viewModel;
    String id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRefundBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.refundAmountToolbar.title.setText("Refund");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
        id = pm.getData(Split.getAppContext(), "Id");
        viewModel = new WalletBalanceViewModel(id, "", "");
        viewModel.init();
        viewModel.getBalance().observe(getViewLifecycleOwner(),walletBalanceModel -> {
            if (walletBalanceModel.isStatus()){
                binding.availableBalanceValue.setText("₹ " + walletBalanceModel.getWalletBalance());
            }
        });

        initClickListeners();
    }

    private void initClickListeners() {
        binding.refundAmountToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnNext.setOnClickListener(view -> {

            String amount = binding.withdrawAmount.getText().toString().trim();
            if (!amount.isEmpty()){
                Bundle bundle = new Bundle();
                bundle.putString("Refund_Amount",amount);
                Navigation.findNavController(view).navigate(R.id.action_refund2_to_enterUpiId,bundle);

            }
        });



    }
}