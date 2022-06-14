package split.com.app.ui.main.view.join_group_search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinSearchBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class JoinSearch extends Fragment {

   FragmentJoinSearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.title.setText("Search");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}