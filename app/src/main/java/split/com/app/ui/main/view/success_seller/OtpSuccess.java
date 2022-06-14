package split.com.app.ui.main.view.success_seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentOtpSuccessBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Split;


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
            Navigation.findNavController(view1).navigate(R.id.home2);
        });

    }
}