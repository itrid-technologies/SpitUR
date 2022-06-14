package split.com.app.ui.main.view.join_checkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinCheckOutBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class JoinCheckOut extends Fragment {

   FragmentJoinCheckOutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckOutBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.toolbar.title.setText("Join");
        binding.profile.count.setText("143 coins");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}