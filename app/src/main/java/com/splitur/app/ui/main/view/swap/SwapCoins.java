package com.splitur.app.ui.main.view.swap;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSwapCoinsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.swap_viewmodel.SwapViewModel;
import com.splitur.app.utils.Split;


public class SwapCoins extends Fragment {


    FragmentSwapCoinsBinding binding;
    String coins;
    SwapViewModel viewModel;
    int coins_int;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSwapCoinsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.swapToolbar.title.setText("Swap");

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applyGradient();

        if (getArguments() != null){
            coins = getArguments().getString("Coins");
            coins_int = Math.round(Float.parseFloat(coins));
            binding.urCoinsValue.setText(String.valueOf(coins_int));
        }

        binding.inrCoinsValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

            }
        });

        initClickListeners();
    }

    private void applyGradient() {
        TextPaint paint = binding.inrCoinsValue.getPaint();
        float width = paint.measureText("Tianjin, China");

        Shader textShader = new LinearGradient(0, 0, width, binding.inrCoinsValue.getTextSize(),
                new int[]{
                        Color.parseColor("#D703FF"),
                        Color.parseColor("#0027FE")
                }, null, Shader.TileMode.CLAMP);
        binding.inrCoinsValue.getPaint().setShader(textShader);

    }


    private void initClickListeners() {

        binding.swapToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.swapButton.setOnClickListener(view -> {
            String swapValue = binding.inrCoinsValue.getText().toString();
            int refund_int = Integer.parseInt(swapValue);
            if (refund_int >= coins_int){
                binding.inrCoinsValue.setText(String.valueOf(Math.round(coins_int)));
            }
            if (refund_int > 0){
                if (!swapValue.isEmpty() && !swapValue.equalsIgnoreCase("0")){

                    viewModel = new SwapViewModel(swapValue);
                    viewModel.init();
                    viewModel.getCoins_data().observe(getViewLifecycleOwner(), basicModel -> {
                        if (basicModel.isStatus()){
                            Navigation.findNavController(view).navigate(R.id.action_swapCoins_to_successfullySwapped);
                        }
                    });
                }
            }else {

            }

        });

        binding.tvMax.setOnClickListener(view -> {
            String availableCoin = binding.urCoinsValue.getText().toString().trim();
            binding.inrCoinsValue.setText(availableCoin);
        });
    }
}