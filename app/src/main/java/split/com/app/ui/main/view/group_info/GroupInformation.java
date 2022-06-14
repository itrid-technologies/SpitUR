package split.com.app.ui.main.view.group_info;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.databinding.FragmentGroupInformationBinding;
import split.com.app.ui.main.adapter.HomeSectionAdapter;
import split.com.app.ui.main.adapter.VerificationStatusAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Split;


public class GroupInformation extends Fragment {

    FragmentGroupInformationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupInformationBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.toolbar.title.setText("Group");
        binding.profile.count.setText("143 coins");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });

        setPasswordVerificationList();


    }

    private void setPasswordVerificationList() {
        List<String> status = new ArrayList<>();
        status.add("Verified");
        status.add("Pending");
        status.add("Invalid");
        status.add("Invalid");

        List<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_verify);
        icons.add(R.drawable.ic_attach);
        icons.add(R.drawable.ic_close);
        icons.add(R.drawable.ic_close);

        List<String> colors = new ArrayList<>();
        colors.add("#0FB900");
        colors.add("#F7931A");
        colors.add("#FF0000");
        colors.add("#FF0000");

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.verificationStatusList.setLayoutManager(layoutManager);
        VerificationStatusAdapter adapter = new VerificationStatusAdapter(Split.getAppContext(), status, icons, colors);
        binding.verificationStatusList.setAdapter(adapter);


    }
}