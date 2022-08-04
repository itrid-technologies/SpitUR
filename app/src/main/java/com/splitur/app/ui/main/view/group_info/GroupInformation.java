package com.splitur.app.ui.main.view.group_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.data.model.group_score.GroupSplitScoreItem;
import com.splitur.app.databinding.FragmentGroupInformationBinding;
import com.splitur.app.ui.main.adapter.VerificationStatusAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.group_info.GroupInfoViewModel;
import com.splitur.app.ui.main.viewmodel.join_group.JoinGroupViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;
import com.splitur.app.utils.TimeAgo;


public class GroupInformation extends Fragment {

    FragmentGroupInformationBinding binding;
    String id;
    private JoinGroupViewModel viewModel;
    DataItem dataItem;

    GroupInfoViewModel groupInfoViewModel;
    List<GroupSplitScoreItem> scoreItems;


    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupInformationBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.giToolbar.title.setText("Group");
//        binding.profile1.count.setText("143 coins");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        scoreItems = new ArrayList<>();

        binding.giToolbar.back.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigateUp();
        });


        binding.joinButton.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String groupData = gson.toJson(dataItem);

            bundle.putString("groupDetail", groupData);
            Navigation.findNavController(view).navigate(R.id.action_groupInformation_to_joinCheckOut, bundle);
        });

        if (getArguments() != null) {
            String data = getArguments().getString("groupDetail");

            Gson gson = new Gson();
            dataItem = gson.fromJson(data, DataItem.class);

            setData(dataItem);
//            viewModel = new JoinGroupViewModel(d);
//            viewModel.init();
//            viewModel.getData().observe(getViewLifecycleOwner(), basicModel -> {
//                if (basicModel.isStatus()){
//
//                }
//            });
        }

        setPasswordVerificationList();

    }

    private void setData(DataItem dataItem) {
        try {
            binding.netflix.setText(dataItem.getTitle());
            binding.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(dataItem.getGroupAdmin().getAvatar())));

//            Glide.with(requireContext())
//                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(binding.userImage);
            binding.userName.setText(String.format("@%s", dataItem.getGroupAdmin().getUserId()));
            binding.tvScoreValue.setText(String.valueOf(Math.round(dataItem.getGroupAdmin().getSpliturScore())));
            if (dataItem.getGroupAdmin().getLastActive() != null) {
                TimeAgo timeAgo = new TimeAgo();
                String last_seen = timeAgo.covertTimeToText(dataItem.getGroupAdmin().getLastActive());
                binding.tvLastActive.setText(last_seen);
            }


            binding.tvDaysValue.setText(Constants.getDate(dataItem.getGroupAdmin().getCreatedAt()));

            String coin = String.valueOf(dataItem.getCostPerMember());
            double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            binding.count.setText(value + " Coins");
            // binding.tvDaysValue.setText(dataItem.);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setPasswordVerificationList() {
        groupInfoViewModel = new GroupInfoViewModel(String.valueOf(dataItem.getId()));
        groupInfoViewModel.init();
        groupInfoViewModel.getData().observe(getViewLifecycleOwner(), groupScoreModel -> {
            if (groupScoreModel.isSuccess()) {
                if (groupScoreModel.getData().getGroupTitle().equalsIgnoreCase(dataItem.getTitle())) {
                    if (groupScoreModel.getData().getGroupSplitScore().size() > 0) {
                        binding.noUserLayout.setVisibility(View.GONE);
                        binding.verificationStatusList.setVisibility(View.VISIBLE);

                        scoreItems.addAll(groupScoreModel.getData().getGroupSplitScore());
                        for (int i=0; i<= scoreItems.size()-1; i++){
                            if (scoreItems.get(i).getSplitGroupUserId() != null){
                                buildScoreList(scoreItems);
                            }else {
                                binding.noUserLayout.setVisibility(View.VISIBLE);
                                binding.verificationStatusList.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        binding.noUserLayout.setVisibility(View.VISIBLE);
                        binding.verificationStatusList.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void buildScoreList(List<GroupSplitScoreItem> scoreItems) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.verificationStatusList.setLayoutManager(layoutManager);
        VerificationStatusAdapter adapter = new VerificationStatusAdapter(Split.getAppContext(), scoreItems);
        binding.verificationStatusList.setAdapter(adapter);
    }
}