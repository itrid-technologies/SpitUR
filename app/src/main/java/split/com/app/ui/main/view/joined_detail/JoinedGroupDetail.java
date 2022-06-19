package split.com.app.ui.main.view.joined_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.DaggerActivity;
import split.com.app.R;
import split.com.app.databinding.FragmentJoinedGroupDetailBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class JoinedGroupDetail extends Fragment {

    FragmentJoinedGroupDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinedGroupDetailBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}