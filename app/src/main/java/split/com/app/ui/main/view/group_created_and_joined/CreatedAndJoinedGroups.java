package split.com.app.ui.main.view.group_created_and_joined;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentCreatedAndJoinedGroupsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class CreatedAndJoinedGroups extends Fragment {

   FragmentCreatedAndJoinedGroupsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedAndJoinedGroupsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.toolbar.title.setText("Group Created");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}