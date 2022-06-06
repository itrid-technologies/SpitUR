package split.com.app.ui.main.view.refund_upi_id;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentEnterUpiIdBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class EnterUpiId extends Fragment {

    FragmentEnterUpiIdBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnterUpiIdBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.btnRefund.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_enterUpiId_to_refundCompletion);
        });

        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }
}