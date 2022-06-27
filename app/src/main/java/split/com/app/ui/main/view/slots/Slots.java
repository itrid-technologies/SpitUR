package split.com.app.ui.main.view.slots;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import split.com.app.R;
import split.com.app.databinding.FragmentSlotsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Slots extends Fragment {

    FragmentSlotsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSlotsBinding.inflate(inflater, container, false);
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