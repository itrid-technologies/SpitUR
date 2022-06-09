package split.com.app.ui.main.view.create_cost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentCostBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Cost extends Fragment {

    FragmentCostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCostBinding.inflate(inflater, container, false);
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

        binding.btnNext.setOnClickListener(view -> {
            String cost = binding.costValue.getText().toString().trim();
            if (!cost.isEmpty()){
                MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                pm.saveData(Split.getAppContext(), "COST", cost);
                Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter cost per member");
            }
        });
    }
}