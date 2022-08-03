package com.splitur.app.ui.main.view.slots;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSlotsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;

import java.math.BigInteger;


public class Slots extends Fragment {

    FragmentSlotsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSlotsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.slottoolbar.title.setText("Slots");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();

    }

    private void initClickListeners() {

        binding.slotValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    int slots_int = Integer.parseInt(editable.toString());
                    if (slots_int > 6) {
                        binding.errorMessage.setVisibility(View.VISIBLE);
                        binding.errorMessage.setText("Maximum Slots are 6");
                        binding.slotValue.setText("6");
                    } else {
                        binding.errorMessage.setVisibility(View.GONE);
                    }
                }
            }
        });

        binding.slottoolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.slotNextButton.setOnClickListener(view -> {
            String slots = binding.slotValue.getText().toString().trim();
            if (slots.isEmpty()){
                binding.errorMessage.setVisibility(View.VISIBLE);
            }else {
                MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
                preferences.saveData(Split.getAppContext(),"SLOTS",slots);
                Constants.SLOTS = slots;
                Navigation.findNavController(view).navigate(R.id.action_slots_to_visibility2);
            }
        });

        binding.increase.setOnClickListener(view -> {
            String slots = binding.slotValue.getText().toString().trim();

            if (slots.isEmpty()){
                binding.errorMessage.setVisibility(View.VISIBLE);
            }else {
                int slot_int = Integer.parseInt(slots);
                if (slot_int < 6) {
                    slot_int = slot_int + 1;
                    String updated_slot = String.valueOf(slot_int);
                    binding.slotValue.setText(updated_slot);
                }
            }
        });

        binding.decrease.setOnClickListener(view -> {
            String slots = binding.slotValue.getText().toString().trim();

            if (slots.isEmpty()){
                binding.errorMessage.setVisibility(View.VISIBLE);
            }else {
                int slot_int = Integer.parseInt(slots);
                if (slot_int > 0) {
                    slot_int = slot_int - 1;
                    String updated_slot = String.valueOf(slot_int);
                    binding.slotValue.setText(updated_slot);
                }
            }
        });
    }
}