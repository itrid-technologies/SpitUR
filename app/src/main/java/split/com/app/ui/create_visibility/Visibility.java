package split.com.app.ui.create_visibility;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentVisibilityBinding;
import split.com.app.ui.dashboard.Dashboard;


public class Visibility extends Fragment {


    FragmentVisibilityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisibilityBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickListeners();
    }

    private void clickListeners() {

        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.privateLayout.setOnClickListener(view -> {
            binding.publicLayout.setBackgroundResource(0);
            binding.publicNote.setVisibility(View.GONE);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateNote.setVisibility(View.VISIBLE);
            binding.privateSelected.setVisibility(View.VISIBLE);
        });

        binding.publicLayout.setOnClickListener(view -> {
            binding.privateLayout.setBackgroundResource(0);
            binding.privateNote.setVisibility(View.GONE);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicNote.setVisibility(View.VISIBLE);
            binding.publicSelected.setVisibility(View.VISIBLE);
        });

        binding.btnNext.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_visibility2_to_cost2);
        });
    }
}