package split.com.app.ui.main.view.create_visibility;

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
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Visibility extends Fragment {


    FragmentVisibilityBinding binding;
    int visibility = 1;

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

            visibility = 0;

            binding.publicLayout.setBackgroundResource(0);
            binding.publicNote.setVisibility(View.GONE);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateNote.setVisibility(View.VISIBLE);
            binding.privateSelected.setVisibility(View.VISIBLE);
        });

        binding.publicLayout.setOnClickListener(view -> {

            visibility = 1;

            binding.privateLayout.setBackgroundResource(0);
            binding.privateNote.setVisibility(View.GONE);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicNote.setVisibility(View.VISIBLE);
            binding.publicSelected.setVisibility(View.VISIBLE);
        });

        binding.btnNext.setOnClickListener(view -> {

            MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
            pm.saveData(Split.getAppContext(), "SLOTS", "4");
            pm.saveData(Split.getAppContext(), "VISIBILITY", String.valueOf(visibility));

            Navigation.findNavController(view).navigate(R.id.action_visibility2_to_cost2);
        });
    }
}