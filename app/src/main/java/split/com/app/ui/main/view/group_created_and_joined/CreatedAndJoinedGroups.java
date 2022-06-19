package split.com.app.ui.main.view.group_created_and_joined;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import split.com.app.R;
import split.com.app.data.model.all_created_groupx.AllCreatedGroupModel;
import split.com.app.data.model.all_created_groupx.DataItem;
import split.com.app.data.repository.created_and_joined.CreatedAndJoinedGroupRepository;
import split.com.app.databinding.FragmentCreatedAndJoinedGroupsBinding;
import split.com.app.ui.main.adapter.all_created_group.AllCreatedGroupAdapter;
import split.com.app.ui.main.adapter.group_detail_adapter.GroupDetailAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.created_and_joined.CreatedAndJoinedViewModel;
import split.com.app.ui.main.viewmodel.group_viewmodel.GroupDetailViewModel;
import split.com.app.utils.Split;


public class CreatedAndJoinedGroups extends Fragment {

   FragmentCreatedAndJoinedGroupsBinding binding;
    private CreatedAndJoinedViewModel viewModel;
    List<DataItem> data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreatedAndJoinedGroupsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);
        binding.gToolbar.title.setText("Group Created");
        binding.gToolbar.back.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        data = new ArrayList<>();

        viewModel = new CreatedAndJoinedViewModel();
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(),allCreatedGroupModel -> {
            if (allCreatedGroupModel.isSuccess()){
                if (allCreatedGroupModel.getData().size() > 0){
                  data.addAll(allCreatedGroupModel.getData());
                  buildRv();
                }
            }
        });

        initClickListeners();

    }

    private void initClickListeners() {
        binding.joinedButton.setOnClickListener(view -> {
            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.joinedButton.setTextColor(Color.WHITE);

            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.createdButton.setTextColor(Color.parseColor("#9395A4"));

            viewModel = new CreatedAndJoinedViewModel();
            viewModel.initJoin();
            viewModel.getJoinData().observe(getViewLifecycleOwner(),groupDetailModel -> {
                if (groupDetailModel.isSuccess()){
                    if (groupDetailModel.getData().size() > 0){

                    }
                }
            });

        });

        binding.createdButton.setOnClickListener(view -> {
            binding.createdButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#246BFD")));
            binding.createdButton.setTextColor(Color.WHITE);

            binding.joinedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
            binding.joinedButton.setTextColor(Color.parseColor("#9395A4"));
        });    }

    private void buildRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.groupslist.setLayoutManager(layoutManager);
        AllCreatedGroupAdapter adapter = new AllCreatedGroupAdapter(Split.getAppContext(), data);
        binding.groupslist.setAdapter(adapter);
    }
}