package split.com.app.ui.main.view.success_seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.databinding.FragmentOtpSuccessBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class OtpSuccess extends Fragment {


    FragmentOtpSuccessBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtpSuccessBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnHome.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), Dashboard.class);
            startActivity(intent);
        });

    }
}