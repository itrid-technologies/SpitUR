package split.com.app.ui.main.view.swap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentSwapCoinsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class SwapCoins extends Fragment {


    FragmentSwapCoinsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSwapCoinsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.toolbar.title.setText("Swap");

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.toolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.swapButton.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_swapCoins_to_successfullySwapped);
        });
    }
}